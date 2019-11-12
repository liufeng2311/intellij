package com.liufeng.common.config;

import com.liufeng.common.security.RedisCache;
import com.liufeng.common.security.RedisCacheManager;
import com.liufeng.common.security.ShiroFilter;
import com.liufeng.common.security.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 不同于Spring Security,Shiro默认不自动开启
 */
@Configuration
public class ShiroConfig {

    //定义路径对应的过滤器规则,匹配原则从上到下,找到第一个适合自己的，所以/**要配置在最后
    private  Map<String, String> urlFilter(){
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/anon/**", "anon"); //该过滤器表示放行,anon == AnonymousFilter
        filterMap.put("/**", "myFilter"); //出上述路径,其他路径都要使用我们自定义的过滤器
        return filterMap;
    }

    //此处开启Shiro验证功能,设置过滤器和验证规则
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //注入我们自定义的filter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("myFilter", new ShiroFilter());  //自定义的Filter只能在此处初始化，不能当做参入传入，否则会报错
        shiroFilter.setFilters(filters);
        shiroFilter.setFilterChainDefinitionMap(urlFilter());
        return shiroFilter;
    }

    //DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor的作用是支持注解
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);  //必须设置为true
        return proxyCreator;
    }

    //开启注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    //管理Shiro的生命周期
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //定义认证和授权
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm relam = new ShiroRealm();
        relam.setCachingEnabled(true);   //开启认证权限缓存，也可以通过一下方法开启单一的缓存，设置单一开启时，此处不起作用
        relam.setAuthenticationCachingEnabled(true);  //开启权限缓存
        relam.setAuthorizationCachingEnabled(true);  //开启认证缓存
        //relam.setCredentialsMatcher(hashedCredentialsMatcher()); //设置密码加密
        return relam;
    }


    //加入缓存管理器(暂不设置)
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cache = new RedisCacheManager();
        return cache;
    }

    //创建权限管理器,通过方法调用失败,必须是有@Bean生成实例
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm,CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        //securityManager.setCacheManager(cacheManager); //设置缓存管理器  (缓存这块序列化时报错，暂未解决，所以不使用缓存管理器)
        return securityManager;
    }

    //定义用户密码的加密方式,在进行认证时,需要将用户输入的密码+加密盐按此加密,与数据库的
    //密码匹配，
    public HashedCredentialsMatcher hashedCredentialsMatcher () {
        HashedCredentialsMatcher  matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        return matcher;
    }
}
