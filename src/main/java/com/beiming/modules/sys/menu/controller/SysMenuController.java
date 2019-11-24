package com.beiming.modules.sys.menu.controller;

import com.beiming.common.utils.ResultModel;
import com.beiming.modules.sys.menu.domain.SysMenuSaveDTO;
import com.beiming.modules.sys.menu.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "系统菜单模块")
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    @Autowired
    ISysMenuService sysMenuService;

    @GetMapping("info/tree/{id}")
    @ApiOperation(value = "获取菜单树")
    @ApiImplicitParam(name = "id", value = "根节点ID", required = true)
    public ResultModel infoTree(@PathVariable("id") Integer id){
        return ResultModel.success(sysMenuService.infoTree(id));
    }

    @PostMapping("modify")
    @ApiOperation(value = "新增菜单")
    public ResultModel modify(@RequestBody @Valid SysMenuSaveDTO menu){
        if(StringUtils.isEmpty(menu.getId())){
            sysMenuService.saveMenu(menu);
        }else {
            sysMenuService.updateMenu(menu);
        }
        return ResultModel.success();
    }

    @GetMapping("del/{id}")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true)
    public ResultModel modify(@PathVariable("id") Integer id){
        sysMenuService.delMenu(id);
        return ResultModel.success();
    }
}
