package com.beiming.common.interceptor;

import com.beiming.modules.base.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器，对公共字段(创建人、创建时间、修改人、修改时间)值
 * 所有的新增修改操作必须使用实体类参数,且必须为登录状态
 * @author zhiguang
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
public class InsertAndUpdateInterceptor extends AbstractService implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        SqlCommandType sqlCommandType = null;  //SQL类型
        MappedStatement mappedStatement = null; //
        Object param = null; //实体类
        Object[] args = invocation.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MappedStatement) {
                mappedStatement = (MappedStatement) args[i];
                sqlCommandType = mappedStatement.getSqlCommandType();
            } else {
                param = args[i];
            }
        }
        if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.INSERT) {
            setProperty(mappedStatement, sqlCommandType, param);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties obj) {

    }

    /**
     * 为对象的操作属性赋值
     */
    private void setProperty(MappedStatement mappedStatement, SqlCommandType sqlCommandType, Object model) {
        if (model == null) {
            return;
        }
        SqlSource sqlSource = mappedStatement.getSqlSource();
        if (sqlSource instanceof RawSqlSource) {
            return;
        }
        Field[] fields = model.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) { // 遍历所有属性
                String fieldName = fields[i].getName(); // 获取属性的名字
                fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (sqlCommandType == SqlCommandType.INSERT) {
                    if ("createTime".equalsIgnoreCase(fieldName)) {
                        Method m = model.getClass().getMethod("set" + fieldName, Date.class);
                        m.invoke(model, new Date());
                    }
                    if ("createUser".equalsIgnoreCase(fieldName)) {
                        Method m = model.getClass().getMethod("set" + fieldName, Integer.class);
                        m.invoke(model, getUser().getId());
                    }

                } else if (sqlCommandType == SqlCommandType.UPDATE) {
                    if ("updateTime".equalsIgnoreCase(fieldName)) {
                        Method m = model.getClass().getMethod("set" + fieldName, Date.class);
                        m.invoke(model, new Date());
                    }
                    if ("updateUser".equalsIgnoreCase(fieldName)) {
                        Method m = model.getClass().getMethod("set" + fieldName, Integer.class);
                        m.invoke(model, getUser().getId());
                    }

                }
            }
        } catch (Exception e) {
            log.error("InsertAndUpdateInterceptor setProperty error {}", e);
        }
    }

}
