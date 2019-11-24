package com.beiming.modules.sys.dict.service.impl;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.utils.AssertUtils;
import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.dict.domain.dto.DictModifyDTO;
import com.beiming.modules.sys.dict.domain.entity.Dict;
import com.beiming.modules.sys.dict.mapper.DictMapper;
import com.beiming.modules.sys.dict.service.IDictService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    public List<Dict> getAllList(BasePageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return getDict();
    }

    @Override
    public Map<String, List<Dict>> getAllMap() {
        List<Dict> all = getDict();
        return all.stream().collect(Collectors.groupingBy(Dict::getType));
    }

    @Override
    public List<Dict> getListByType(String type) {
        return dictMapper.getListByType(type);
    }

    @Override
    public void addDict(Integer uid, DictModifyDTO dict) {
        existCheck(dict.getCode(), dict.getType());
        Dict target = new Dict();
        BeanUtils.copyProperties(dict, target);
        target.setCreateTime(new Date());
        target.setCreateUser(uid);
        int insert = dictMapper.insert(target);
        AssertUtils.checkZero(insert, ResultCodeEnums.BAD_SQL_CHECK, "新增字典表数据失败");
    }


    @Override
    public void updateDict(Integer uid, DictModifyDTO dict) {
        existCheck(dict.getCode(), dict.getType());
        Dict target = new Dict();
        BeanUtils.copyProperties(dict, target);
        target.setUpdateTime(new Date());
        target.setUpdateUser(uid);
        int update = dictMapper.updateByPrimaryKeySelective(target);
        AssertUtils.checkZero(update, ResultCodeEnums.BAD_SQL_CHECK, "更新字典表数据失败");
    }

    @Override
    public void delDict(Integer id) {
        int delete = dictMapper.deleteByPrimaryKey(id);
        AssertUtils.checkZero(delete, ResultCodeEnums.BAD_SQL_CHECK, "删除字典表数据失败");
    }

    //判断字典数据是否已存在
    public void existCheck(String code, String type) {
        int select = dictMapper.getDictByCodeAndType(code, type);
        AssertUtils.checkGraterZero(select, ResultCodeEnums.BAD_SQL_CHECK, "字典表中已存在");
    }

    //获取字典表需要展示的数据
    private List<Dict> getDict() {
        Dict dict = new Dict();
        dict.setShowFlag("0"); //0表示可查看
        return dictMapper.select(dict);
    }
}
