package com.beiming.modules.sys.dict.service;

import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.dict.domain.dto.DictModifyDTO;
import com.beiming.modules.sys.dict.domain.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典表接口
 */
public interface IDictService {

    /**
     * 获取所有字典数据(以Map的形式)
     */
    Map<String, List<Dict>> getAllMap();

    /**
     * 获取所有字典数据(以List的形式)
     */
    List<Dict> getAllList(BasePageQuery page);

    /**
     * 根据Type获取字典数据
     */
    List<Dict> getListByType(String type);

    /**
     * 添加字典数据
     */
    void addDict(DictModifyDTO dict);

    /**
     * 删除字典数据
     */
    void delDict(Integer id);

    /**
     * 更新字典数据
     */
    void updateDict(DictModifyDTO dict);
}
