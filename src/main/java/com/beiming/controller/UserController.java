package com.beiming.controller;

import com.beiming.common.annotation.PageQuery;
import com.beiming.common.utils.ResultModel;
import com.beiming.domian.dto.base.BaseId;
import com.beiming.domian.dto.base.BasePageQuery;
import com.beiming.domian.dto.request.ChangePassRequestDTO;
import com.beiming.domian.dto.request.UserGetRequestDTO;
import com.beiming.domian.dto.request.UserLoginRequestDTO;
import com.beiming.domian.dto.request.UserModifyRequestDTO;
import com.beiming.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@Api(tags = "用户表相关操作")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("all")
    @ApiOperation(value = "获取所有用户表数据")
    @PageQuery
    public ResultModel getList(@RequestBody BasePageQuery page) {
        return ResultModel.success(userService.getAllList(page));
    }

    @PostMapping("userByPhone")
    @ApiOperation(value = "根据手机号获取用户数据")
    public ResultModel ListByType(@RequestBody @Valid UserGetRequestDTO user) {
        return ResultModel.success(userService.getUserByPhone(user.getPhone()));
    }

    @PostMapping("del")
    @ApiOperation(value = "根据ID删除用户表数据")
    public ResultModel del(@RequestBody @Valid BaseId id) {
        userService.delUser(id.getId());
        return ResultModel.success();
    }

    @PostMapping("modify")
    @ApiOperation(value = "修改用户数据")
    public ResultModel listByType(@RequestBody @Valid UserModifyRequestDTO user) {
        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
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
        return ResultModel.success( userService.login(user));
    }
}