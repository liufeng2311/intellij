package com.beiming.service.impl;

import com.beiming.domian.dto.response.DictResponseListDTO;
import com.github.pagehelper.PageHelper;
import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import com.beiming.common.utils.AssertUtils;
import com.beiming.common.utils.DateUtils;
import com.beiming.domian.dto.base.BasePageQuery;
import com.beiming.domian.dto.request.DictGetRequestDTO;
import com.beiming.domian.dto.request.DictModifyRequestDTO;
import com.beiming.domian.entity.Dict;
import com.beiming.mapper.DictMapper;
import com.beiming.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    public List<Dict> getAllList(BasePageQuery page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        return dictMapper.selectAll();
    }

    @Override
    public Map<String, List<Dict>> getAllMap() {
        List<Dict> all = dictMapper.selectAll();
        Map<String, List<Dict>> collect = all.stream().collect(Collectors.groupingBy(Dict::getType));
        return collect;
    }

    @Override
    public List<Dict> getAllListByType(DictGetRequestDTO dict) {
        PageHelper.startPage(dict.getPageNum(),dict.getPageSize());
        return dictMapper.getAllListByType(dict.getType());
    }

    @Override
    public void addDict(DictModifyRequestDTO dict) {
        existCheck(dict.getCode(), dict.getType());
        Dict target = new Dict();
        BeanUtils.copyProperties(dict,target);
        target.setCreateTime(DateUtils.localDateTime2Date(LocalDateTime.now()));
        target.setCreateUser(dict.getUser());
        int count = dictMapper.insert(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public void delDict(Integer id) {
        int count = dictMapper.deleteByPrimaryKey(id);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public void updateDict(DictModifyRequestDTO dict) {
        existCheck(dict.getCode(), dict.getType());
        Dict target = new Dict();
        BeanUtils.copyProperties(dict,target);
        target.setUpdateTime(DateUtils.localDateTime2Date(LocalDateTime.now()));
        target.setUpdateUser(dict.getUser());
        int count = dictMapper.updateByPrimaryKeySelective(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public List<DictResponseListDTO> getDictByType(String type) {
        return dictMapper.getDictByType(type);
    }

    //判断字典数据是否已存在
    public void existCheck(String code, String type){
        int select = dictMapper.getDictByCodeAndType(code, type);
        if(select == 1){
            throw new BusinessException(ResultCodeEnums.BAD_SQL_CHECK, "字典表中已存在");
        }
    }
}
