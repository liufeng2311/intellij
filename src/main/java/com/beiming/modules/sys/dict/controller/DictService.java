package com.beiming.modules.sys.dict.controller;

import com.beiming.common.utils.ResultModel;
import com.beiming.modules.base.service.AbstractService;
import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.dict.domain.dto.DictModifyDTO;
import com.beiming.modules.sys.dict.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("dict")
@Api(tags = "字典表相关操作")
public class DictService extends AbstractService {

    @Autowired
    IDictService dictService;

    @GetMapping("info/map")
    @ApiOperation(value = "获取所有字典表数据(Map)")
    public ResultModel getAllMap() {
        return ResultModel.success(dictService.getAllMap());
    }

    @PostMapping("info/list")
    @ApiOperation(value = "获取所有字典表数据(List)")
    public ResultModel getAllList(@RequestBody @Valid BasePageQuery page) {
        return ResultModel.success(dictService.getAllList(page));
    }

    @PostMapping("info/{type}")
    @ApiOperation(value = "根据Type获取字典表数据")
    @ApiImplicitParam(name = "type", value = "字典类型", required = true)
    public ResultModel ListByType(@PathVariable("type") String type) {
        return ResultModel.success(dictService.getListByType(type));
    }

    @GetMapping("del/{id}")
    @ApiOperation(value = "根据ID删除字典表数据")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public ResultModel del(@PathVariable("id") Integer id) {
        dictService.delDict(id);
        return ResultModel.success();
    }

    @PostMapping("modify")
    @ApiOperation(value = "新增/修改字典数据")
    public ResultModel ListByType(@RequestBody @Valid DictModifyDTO dict) {
        Integer uid = getUserId();
        if (StringUtils.isEmpty(dict.getId().toString())) {
            dictService.addDict(uid, dict);
        } else {
            dictService.updateDict(uid, dict);
        }
        return ResultModel.success();
    }
}
