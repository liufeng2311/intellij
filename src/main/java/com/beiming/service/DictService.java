package com.beiming.service;

import com.beiming.domian.dto.base.BasePageQuery;
import com.beiming.domian.dto.request.DictGetRequestDTO;
import com.beiming.domian.dto.request.DictModifyRequestDTO;
import com.beiming.domian.dto.response.DictResponseListDTO;
import com.beiming.domian.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典表接口
 */
public interface DictService {

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
    List<Dict> getAllListByType(DictGetRequestDTO dict);

    /**
     * 添加字典数据,修改时必须判断code和type的唯一性
     */
    void addDict(DictModifyRequestDTO dict);

    /**
     * 删除字典数据
     */
    void delDict(Integer id);

    /**
     * 更新字典数据
     */
    void updateDict(DictModifyRequestDTO dict);

    /**
     * 获取某一类型的数据集合
     */
    List<DictResponseListDTO> getDictByType(String type);
}
