package com.liufeng.controller;

import com.liufeng.common.annotation.PageQuery;
import com.liufeng.common.async.AsyncTask;
import com.liufeng.common.utils.ResultModel;
import com.liufeng.domian.dto.base.BaseId;
import com.liufeng.domian.dto.base.BasePageQuery;
import com.liufeng.domian.dto.request.DictGetRequestDTO;
import com.liufeng.domian.dto.request.DictModifyRequestDTO;
import com.liufeng.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("dict")
@Api(tags = "字典表相关操作")
public class DictController {

    @Autowired
    DictService dictService;

    @PostMapping("allMap")
    @ApiOperation(value = "获取所有字典表数据(Map)")
    public ResultModel getAll() {
        return ResultModel.success(dictService.getAllMap());
    }

    @PostMapping("allList")
    @ApiOperation(value = "获取所有字典表数据(List)")
    @PageQuery
    public ResultModel getList(@RequestBody BasePageQuery page) {
        return ResultModel.success(dictService.getAllList(page));
    }

    @PostMapping("listByType")
    @ApiOperation(value = "根据Type获取字典表数据")
    @PageQuery
    public ResultModel ListByType(@RequestBody @Valid DictGetRequestDTO dict) {
        return ResultModel.success(dictService.getAllListByType(dict));
    }

    @PostMapping("del")
    @ApiOperation(value = "根据ID删除字典表数据")
    public ResultModel del(@RequestBody @Valid BaseId id) {
        dictService.delDict(id.getId());
        return ResultModel.success();
    }

    @PostMapping("modify")
    @ApiOperation(value = "根据Type获取字典表数据")
    public ResultModel ListByType(@RequestBody @Valid DictModifyRequestDTO dict) {
        if (dict.getId() == null) {
            dictService.addDict(dict);
        } else {
            dictService.updateDict(dict);
        }
        return ResultModel.success();
    }
}
