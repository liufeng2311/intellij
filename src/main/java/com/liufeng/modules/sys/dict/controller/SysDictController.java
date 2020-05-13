package com.beiming.modules.sys.dict.controller;

import com.beiming.common.utils.ResultModel;
import com.beiming.modules.sys.dict.domain.dto.SysDictModifyDTO;
import com.beiming.modules.sys.dict.domain.dto.SysDictQueryDTO;
import com.beiming.modules.sys.dict.domain.vo.SysDictVO;
import com.beiming.modules.sys.dict.service.SysDictService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典表")
@RestController
@RequestMapping("sys-dict")
//@RequiresRoles({"dict:sys-dict"})
public class SysDictController {

    @Autowired
    SysDictService sysDictService;

    @PostMapping("list")
    @ApiOperation(value = "字典列表")
    public ResultModel list(@Valid @RequestBody SysDictQueryDTO query) {
        PageInfo<SysDictVO> list = sysDictService.list(query);
        return ResultModel.success(list);
    }

    @PostMapping("insert")
    @ApiOperation(value = "字典新增")
    public ResultModel insert(@Valid @RequestBody SysDictModifyDTO save) {
        sysDictService.insert(save);
        return ResultModel.success();
    }

    @PostMapping("update")
    @ApiOperation(value = "字典修改")
    public ResultModel update(@Valid @RequestBody SysDictModifyDTO update) {
        sysDictService.update(update);
        return ResultModel.success();
    }

    @GetMapping("info/{id}")
    @ApiOperation(value = "字典回显")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public ResultModel info(@PathVariable("id") Integer id) {
        SysDictVO info = sysDictService.info(id);
        return ResultModel.success(info);
    }

    @PostMapping("delete")
    @ApiOperation(value = "字典删除")
    public ResultModel delete(List<Integer> delete) {
        sysDictService.delete(delete);
        return ResultModel.success();
    }
}