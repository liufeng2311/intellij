package com.liufeng.common.aspect;

import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 是否对分页参数验证的切面
 */
@Aspect
@Component
public class PageQueryAspect {

    @Pointcut("execution(* com.liufeng.controller..*.*(..)) && @annotation(com.liufeng.common.annotation.PageQuery)")
    public void check() {
    }

    @Before("check()")
    public void pageQueryCheck(JoinPoint point) throws IllegalAccessException {
        Object object = point.getArgs()[0];
        if (object == null) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields(); //获取所有的属性,分页属性都位于父类中
        for (Field var : fields) {
            if (var.getGenericType().toString().equals("class java.lang.Integer") &&
                    (var.getName().equals("pageNum") || (var.getName().equals("pageSize")))) {
                var.setAccessible(true);  //破坏类的private属性
                if (StringUtils.isEmpty(var.get(object))) {
                    throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "分页参数为空");
                }
                Integer o = (Integer) var.get(object);
                if(o <= 0){
                    throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "分页参数值必须大于零");
                }
            }
        }
    }
}
