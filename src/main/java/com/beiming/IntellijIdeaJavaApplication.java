package com.beiming;

import com.beiming.modules.sys.user.domain.entity.SysUser;
import com.beiming.modules.sys.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAsync  //开启异步任务
@EnableScheduling //开启定时任务
@RestController
@RequestMapping("test")
public class IntellijIdeaJavaApplication extends SpringBootServletInitializer {

    @Autowired
    SysUserMapper  sysUserMapper;

    /**
     * 重写SpringBootServletInitializer中的该方法实现打war包,同时打包时排除自带的tomcat以及设置打包为war
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IntellijIdeaJavaApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(IntellijIdeaJavaApplication.class, args);
    }

    @RequestMapping("/anon/test")
    public Object test(){
        SysUser user = new SysUser();
        user.setUsername("刘峰");
        user.setPassword("123455");
        user.setLockStatus("0");
        sysUserMapper.insert(user);
        return user;
    }
}
