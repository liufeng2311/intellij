package com.beiming.common.annotation;

import com.beiming.common.paramcheck.TimeIntervalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证参数时间段注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeIntervalValidator.class)
public @interface CheckTimeInterval {

    //开始时间
    String startTime() default "startTime";

    //结束时间
    String endTime() default "endTime";

    //时间格式
    String format() default "yyyy-MM-dd";

    //开始字段名
    String startName() default "开始时间";

    //结束字段名
    String endName() default "结束时间";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
