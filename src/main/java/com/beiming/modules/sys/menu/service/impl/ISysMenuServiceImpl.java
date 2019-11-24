package com.beiming.modules.sys.menu.service.impl;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import com.beiming.common.utils.AssertUtils;
import com.beiming.modules.base.service.AbstractService;
import com.beiming.modules.sys.menu.domain.SysMenuSaveDTO;
import com.beiming.modules.sys.menu.domain.entity.SysMenu;
import com.beiming.modules.sys.menu.domain.vo.SysMenuVO;
import com.beiming.modules.sys.menu.mapper.SysMenuMapper;
import com.beiming.modules.sys.menu.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ISysMenuServiceImpl extends AbstractService implements ISysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuVO> infoTree(Integer id) {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        List<SysMenuVO> transform = transformVO(sysMenus);
        return buildTree(id, transform);
    }

    @Override
    public void saveMenu(SysMenuSaveDTO menu) {
        int order = sysMenuMapper.maxOrder(menu.getParentId());   //此处最大序号多人同时操作时可能会出现重复,数据库中使用联合主键保障唯一性
        SysMenu target = transformDTO(menu);
        target.setCreateTime(new Date());
        target.setCreateUser(getUserId());
        target.setOrder(order + 1);
        int insert = sysMenuMapper.insert(target);
        AssertUtils.checkZero(insert, ResultCodeEnums.BAD_SQL_CHECK, "新增菜单失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuSaveDTO menu) {
        SysMenu target = transformDTO(menu);
        target.setId(Integer.valueOf(menu.getId()));
        target.setUpdateTime(new Date());
        target.setUpdateUser(getUserId());
        int update = sysMenuMapper.updateByPrimaryKeySelective(target);
        AssertUtils.checkZero(update, ResultCodeEnums.BAD_SQL_CHECK, "修改菜单失败");
    }

    @Override
    public void delMenu(Integer id) {
        SysMenu menu = new SysMenu();
        menu.setParentId(id);
        List<SysMenu> select = sysMenuMapper.select(menu);
        if(select.size() > 0){
            throw new BusinessException(ResultCodeEnums.VAILD_MENU,"请先删除子菜单");
        }
        sysMenuMapper.deleteByPrimaryKey(id);
    }


    //构建菜单树
    private List<SysMenuVO> buildTree(Integer id, List<SysMenuVO> menu) {
        List<SysMenuVO> target = new ArrayList<>();
        List<SysMenuVO> collect = menu.stream()
                .filter(x -> x.getParentId() == id)  //查询所有的子节点数据
                .sorted(Comparator.comparingInt(SysMenuVO::getOrder))  //按照order升序
                .collect(Collectors.toList());
        if (collect.size() == 0) { //设置迭代返回条件
            return target;
        }
        target.addAll(collect);
        menu.removeAll(collect); //移除已添加的数据,减少后续循环次数
        target.stream().forEach(x -> { //迭代赋值
            x.setChild(buildTree(x.getId(), menu));
        });
        return target;
    }

    //实体类转化为VO
    private List<SysMenuVO> transformVO(List<SysMenu> sysMenus) {
        List<SysMenuVO> collect =
                sysMenus.stream().map(x -> SysMenuVO.builder().id(x.getId())
                        .parentId(x.getParentId())
                        .name(x.getName())
                        .url(x.getUrl())
                        .permit(x.getPermit())
                        .icon(x.getIcon())
                        .order(x.getOrder())
                        .build())
                        .collect(Collectors.toList());
        return collect;
    }


    //DTO转实体类(新增、修改)
    private SysMenu transformDTO(SysMenuSaveDTO menu) {
        SysMenu build = SysMenu.builder()
                .parentId(menu.getParentId())
                .name(menu.getName())
                .url(menu.getUrl())
                .permit(menu.getPermit())
                .icon(menu.getIcon())
                .showFlag(menu.getShowFlag())
                .build();
        return build;
    }

    //获取某菜单下所有子节点的ID
    private List<Integer> getChildIds(Integer id, List<SysMenuVO> menu, List<Integer> target) {
        List<SysMenuVO> collect = menu.stream()
                .filter(x -> x.getParentId() == id)
                .collect(Collectors.toList());
        if (collect.size() == 0) { //设置迭代返回条件
            return target;
        }
        target.addAll(collect.stream().map(SysMenuVO::getId).collect(Collectors.toList()));
        menu.removeAll(collect); //移除已添加的数据,减少后续循环次数
        collect.stream().forEach(x -> { //迭代赋值
            getChildIds(x.getId(),menu,target);
        });
        return target;
    }
}
