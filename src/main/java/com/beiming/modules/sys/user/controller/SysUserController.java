package com.beiming.modules.sys.user.controller;

import com.beiming.common.utils.ResultModel;
import com.beiming.modules.base.service.AbstractService;
import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.user.domain.dto.ChangePassRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserGetRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserLoginRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserModifyRequestDTO;
import com.beiming.modules.sys.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@Api(tags = "用户表相关操作")
public class SysUserController extends AbstractService {

    @Autowired
    ISysUserService userService;

    @PostMapping("info")
    @ApiOperation(value = "获取所有用户表数据")
    public ResultModel getList(@RequestBody @Valid BasePageQuery page) {
        return ResultModel.success(userService.getAllList(page));
    }

    @PostMapping("info/{phone}")
    @ApiOperation(value = "根据手机号获取用户数据")
    @ApiImplicitParam(name = "phone", value ="手机号")
    public ResultModel ListByType(@PathVariable("phone") String phone) {
        return ResultModel.success(userService.getUserByPhone(phone));
    }

    @PostMapping("info/{id}")
    @ApiOperation(value = "根据手机号获取用户数据")
    @ApiImplicitParam(name = "phone", value ="手机号", required = true)
    public ResultModel ListById(@PathVariable("id") Integer id) {
        return ResultModel.success(userService.getUserById(id));
    }

    @PostMapping("del/{id}")
    @ApiOperation(value = "根据ID删除用户表数据")
    @ApiImplicitParam(name = "id", value ="id", required = true)
    public ResultModel del(@PathVariable("id") Integer id) {
        userService.delUser(id);
        return ResultModel.success();
    }

    @PostMapping("modify")
    @ApiOperation(value = "修改用户数据")
    public ResultModel listByType(@RequestBody @Valid UserModifyRequestDTO user) {
        Integer userId = getUserId();
        if (StringUtils.isEmpty(user.getId())) {
            userService.addUser(userId, user);
        } else {
            userService.updateUser(userId, user);
        }
        return ResultModel.success();
    }

    @PostMapping("anon/verifyCode")
    @ApiOperation(value = "发送验证码")
    public ResultModel sendCode(@RequestBody @Valid UserGetRequestDTO user) {
        return ResultModel.success(userService.sendVerityCode(user.getPhone()));
    }

    @PostMapping("anon/changePass")
    @ApiOperation(value = "修改密码")
    public ResultModel changePass(@RequestBody @Valid ChangePassRequestDTO user) {
        userService.changePass(user);
        return ResultModel.success();
    }

    @PostMapping("anon/login")
    @ApiOperation(value = "用户登录")
    public ResultModel login(@RequestBody @Valid UserLoginRequestDTO user) {
        return ResultModel.success(userService.login(user));
    }
}
