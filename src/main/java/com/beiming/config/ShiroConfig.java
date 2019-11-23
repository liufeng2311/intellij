package com.beiming.config;

import com.beiming.common.security.ShiroFilter;
import com.beiming.common.security.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
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
    private Map<String, String> urlFilter() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/*/anon/**", "anon"); //该过滤器表示放行,anon == AnonymousFilter
        filterMap.put("/**/swagger-ui.html/**", "anon");  //配置Swagger配置不被拦截
        filterMap.put("/**/swagger/**", "anon");
        filterMap.put("/**/swagger-resources/**", "anon");
        filterMap.put("/**/v2/**", "anon");
        filterMap.put("/**/webjars/**", "anon");
        filterMap.put("/**/configuration/**", "anon");
        filterMap.put("/**", "myFilter"); //除上述路径,其他路径都要使用我们自定义的过滤器
        return filterMap;
    }

    //通过配置ShiroFilterFactoryBean启动shiro
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("myFilter", new ShiroFilter());  //自定义的Filter只能在此处初始化，不能当做参入传入，否则会报错
        shiroFilter.setFilters(filters); //将我们自定义的过滤器交由shiro管理
        shiroFilter.setFilterChainDefinitionMap(urlFilter()); //定义过滤条件
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
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm relam = new ShiroRealm();
        return relam;
    }

    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }
}
