package com.beiming.common.aspect;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分页切面(未用到)
 */
@Aspect
@Component
public class PageQueryAspect {

    @Pointcut("execution(* com.beiming..*.controller..*.*(..)) && @annotation(com.beiming.common.annotation.PageQuery)")
    public void pageQuerycheck() {
    }

    @Before("pageQuerycheck()")
    public void pageQueryCheck(JoinPoint point) throws Exception {
        Object object = point.getArgs()[0];
        if (object == null) {
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "@PageQuery注解修饰的方法必须含有@RequestBody修饰的参数");
        }
        Field[] fields = getAllFiled(object);
        for (Field var : fields) {
            if (var.getGenericType().toString().equals("class java.lang.Integer") &&
                    (var.getName().equals("pageNum") || (var.getName().equals("pageSize")))) {
                var.setAccessible(true);  //破坏类的private属性
                if (StringUtils.isEmpty(var.get(object))) {
                    throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, new StringBuffer().append(var.getName()).append(" is null").toString());
                }
                Integer o = (Integer) var.get(object);
                if (o <= 0) {
                    throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "分页参数必须大于零");
                }
            }
        }
    }

    //获取基类和父类中的所有属性
    private Field[] getAllFiled(Object object) {
        List<Field> list = new ArrayList<Field>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            list.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()))); //getDeclaredFields只能获取不到父类中的属性
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[list.size()];
        list.toArray(fields);
        return fields;
    }
}
