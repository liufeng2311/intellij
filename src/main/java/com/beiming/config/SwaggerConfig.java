package com.beiming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value(value = "${swagger.enable}")
  Boolean swaggerEnabled;

  @Bean
  public Docket createRestApi() {
    ParameterBuilder tokenParam = new ParameterBuilder();
    List<Parameter> params = new ArrayList<Parameter>();
    tokenParam.name("Authorization").description("令牌标识").modelRef(new ModelRef("string"))
        .parameterType("header").required(true).build();
    params.add(tokenParam.build());
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.beiming.controller"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(params)
        .apiInfo(apiInfo())
        .enable(swaggerEnabled).pathMapping("");
  }


  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("数据提供服务的 API") //文档标题
        .description("接口列表")
        .version("1.0.0") //版本
        .build();
  }

}
