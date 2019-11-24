package com.beiming.modules.sys.menu.service;

import com.beiming.modules.sys.menu.domain.SysMenuSaveDTO;
import com.beiming.modules.sys.menu.domain.entity.SysMenu;
import com.beiming.modules.sys.menu.domain.vo.SysMenuVO;

import java.util.List;

public interface ISysMenuService {

    /**
     * 获取菜单数据,以树的形式返回
     * @param id 菜单的根节点ID
     */
    List<SysMenuVO> infoTree(Integer id);

    /**
     * 新增菜单
     * @param menu
     */
    void saveMenu(SysMenuSaveDTO menu);

    /**
     * 修改菜单,单修改
     * @param menu
     */
    void updateMenu(SysMenuSaveDTO menu);

    /**
     * 删除菜单,单删除
     * @param id
     */
    void delMenu(Integer id);
}
