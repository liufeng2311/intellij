package com.beiming.modules.sys.dict.controller;

import com.beiming.modules.sys.dict.service.SysDictService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "字典表")
@RestController
@RequestMapping("user/sys-dict")
public class SysDictController {

    @Autowired
    SysDictService SysDictService;
}