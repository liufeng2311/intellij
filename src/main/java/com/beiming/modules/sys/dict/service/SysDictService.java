package com.beiming.modules.sys.dict.service;

import com.beiming.modules.sys.dict.domain.dto.SysDictModifyDTO;
import com.beiming.modules.sys.dict.domain.dto.SysDictQueryDTO;
import com.beiming.modules.sys.dict.domain.vo.SysDictVO;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface SysDictService {

    /**
     * 列表
     */
    PageInfo<SysDictVO> list(SysDictQueryDTO query);

    /**
     * 新增
     */
    void insert(SysDictModifyDTO save);

    /**
     * 更新
     */
    void update(SysDictModifyDTO update);

    /**
     * 详情
     */
    SysDictVO info(Integer id);

    /**
     * 删除
     */
    void delete(List<Integer> delete);
}