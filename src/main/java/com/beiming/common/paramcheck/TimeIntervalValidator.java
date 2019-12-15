package com.beiming.common.paramcheck;

import com.beiming.common.annotation.CheckTimeInterval;
import com.beiming.common.utils.DateUtils;
import com.beiming.common.utils.PatternUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证开始时间必须小于结束时间
 */
public class TimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {

    //开始时间
    private String start;

    //结束时间
    private String end;

    //日期格式
    private String format;

    //开始字段名
    private String startName;

    //结束字段名
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
        if(value == null){
            return false;
        }
        BeanWrapper bean = new BeanWrapperImpl(value);
        String startTime = (String) bean.getPropertyValue(start);
        String endTime = (String) bean.getPropertyValue(end);
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            context.buildConstraintViolationWithTemplate(startName + "、" + endName + "不能为空")
                    .addConstraintViolation();
            return false;
        }
        if(!DateUtils.localDateString2Date(startTime, format).before(DateUtils.localDateString2Date(endTime, format))){
            context.buildConstraintViolationWithTemplate(startName + "不能晚于" + endName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
