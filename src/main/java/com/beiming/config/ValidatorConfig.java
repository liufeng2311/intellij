package com.beiming.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ValidatorConfig {

    //参数验证默认全部验证完之后才返回,此处我们设置为快速返回,即有一处验证失败就不进行之后的验证
    @Bean
    public Validator validator() {
        Validator validator = Validation.byProvider(HibernateValidator.class)
                .configure().failFast(true)
                .buildValidatorFactory()
                .getValidator();
        return validator;
    }
}
