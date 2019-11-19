package com.beiming.common.config;

import com.beiming.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 *	继承WebMvcConfigurer类，可以实现对SpringmvcMVC的定制化配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	//配置跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
					.allowedHeaders("*")
					.allowedOrigins("*")
					.allowedMethods("*")
					.exposedHeaders("access-control-allow-headers",
						"access-control-allow-methods",
						"access-control-allow-origin",
						"access-control-max-age",
						"X-Frame-Options")
					.allowCredentials(false)  //true表示浏览器访问时会写到cookie信息,我们使用的token,设置为false
					.maxAge(3600); //用来指定本次预检请求的有效期，单位为秒。在有效期间，不用发出另一条预检请求
	}

	//配置拦截器和拦截规则
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				    .addPathPatterns("/**");
	}

	//配置静态资源映射
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**")
					.addResourceLocations("classpath*: /templates/");
	}

	//配置页面跳转
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index")
					.setViewName("/index");
	}

	//配置url匹配规则(默认路径中如果有.的话，.后面的值会被忽略)
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false); //此处设置为false,不会忽略.后面的值
	}
	
	
}
