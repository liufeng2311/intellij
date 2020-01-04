package com.beiming.common.annotation.param;

import com.beiming.common.utils.DateUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证参数时间段注解
 * 开始时间需要小于结束时间
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckTimeIntervals.class)
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

    Class<? extends Payload>[] payload() default {};

}


//设置该注解为可重复注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface CheckTimeIntervals {
    CheckTimeInterval[] value();
}


//自定义注解验证逻辑
class TimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {

    private String start;

    private String end;

    private String format;

    private String startName;

    private String endName;

    //获取注解中的值
    @Override
    public void initialize(CheckTimeInterval constraintAnnotation) {
        this.start = constraintAnnotation.startTime();
        this.end = constraintAnnotation.endTime();
        this.format = constraintAnnotation.format();
        this.startName = constraintAnnotation.startName();
        this.endName = constraintAnnotation.endName();
    }

    //判断是否放行
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation(); //禁用默认提示信息
        if (value == null) {
            return false;
        }
        BeanWrapper bean = new BeanWrapperImpl(value);
        String startTime = (String) bean.getPropertyValue(start);
        String endTime = (String) bean.getPropertyValue(end);
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            context.buildConstraintViolationWithTemplate(startName + "、" + endName + "不能为空")
                    .addConstraintViolation();
            return false;
        }
        if (!DateUtils.localDateString2Date(startTime, format).before(DateUtils.localDateString2Date(endTime, format))) {
            context.buildConstraintViolationWithTemplate(startName + "不能晚于" + endName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
