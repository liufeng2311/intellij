package com.beiming.modules.sys.dict.service.impl;

import com.beiming.modules.base.service.AbstractService;
import com.beiming.modules.sys.dict.domain.dto.SysDictModifyDTO;
import com.beiming.modules.sys.dict.domain.dto.SysDictQueryDTO;
import com.beiming.modules.sys.dict.domain.entity.SysDict;
import com.beiming.modules.sys.dict.domain.vo.SysDictVO;
import com.beiming.modules.sys.dict.mapper.SysDictMapper;
import com.beiming.modules.sys.dict.service.SysDictService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.stream.Collectors;;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends AbstractService implements SysDictService {

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public PageInfo<SysDictVO> list(SysDictQueryDTO query) {
        SysDict target = new SysDict();
        BeanUtils.copyProperties(query, target);
        Page<SysDictVO> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysDict> list = sysDictMapper.select(target);
        PageInfo<SysDictVO> pageInfo = new PageInfo<>(page);
        pageInfo.setList(list.stream().map(x -> dto2vo(x)).collect(Collectors.toList()));
        return pageInfo;
    }

    @Override
    public void insert(SysDictModifyDTO save) {
        SysDict target = new SysDict();
        BeanUtils.copyProperties(save, target);
        sysDictMapper.insert(target);
    }

    @Override
    public void update(SysDictModifyDTO update) {
        SysDict target = new SysDict();
        BeanUtils.copyProperties(update, target);
        sysDictMapper.updateByPrimaryKeySelective(target);
    }

    @Override
    public SysDictVO info(Integer id) {
        SysDict sysDict = sysDictMapper.selectByPrimaryKey(id);
        SysDictVO sysDictVO = dto2vo(sysDict);
        return sysDictVO;
    }

    @Override
    public void delete(List<Integer> delete) {
        for(Integer id : delete){
            sysDictMapper.deleteByPrimaryKey(id);
        }
    }

    //DTO转VO
    private SysDictVO dto2vo(SysDict source) {
        SysDictVO target = new SysDictVO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}