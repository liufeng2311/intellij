package com.beiming.modules.sys.dict.service.impl;

import com.beiming.modules.base.service.AbstractService;
import com.beiming.modules.sys.dict.mapper.SysDictMapper;
import com.beiming.modules.sys.dict.service.SysDictService;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends AbstractService implements SysDictService {

    @Autowired
    SysDictMapper SysDictMapper;
}