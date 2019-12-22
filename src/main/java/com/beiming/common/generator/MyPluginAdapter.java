package com.beiming.common.generator;

import com.beiming.modules.base.mapper.BaseMapper;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.UpdateByExampleWithoutBLOBsMethodGenerator;
import org.mybatis.generator.config.TableConfiguration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PluginAdapter用来指定是否生成指定代码, true 表示生成, false 表示不生成
 * 我们使用lombok,此处设置实体类不生成getter/setter方法
 */
public class MyPluginAdapter extends PluginAdapter {

    private static final List<String > ids = new ArrayList<>(Arrays.asList("id", "ID", "Id")); //数据库中ID字段的定义

    private String baseMapper;

    private String mapperPackage;

    private String entityPackage;

    private String targetProject;

    @Override
    public boolean validate(List<String> list) {
        baseMapper = properties.getProperty("baseMapper");
        mapperPackage = properties.getProperty("mapperPackage");
        entityPackage = properties.getProperty("entityPackage");
        targetProject = properties.getProperty("targetProject");
        return true;
    }

    /**
     * 不生成get方法
     *
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }


    /**
     * 不生成set方法
     *
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 配置文件中设置enableSelectByPrimaryKey为true(全部设置为false时,不生成xml)
     * 此处关闭生成
     */
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成BaseColumn
     */
    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }



    /**
     * 生成Mapper相关配置
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        for(TableConfiguration var : context.getTableConfigurations()){
        String name = var.getDomainObjectName(); //获取生成的实体类名称
        JavaFormatter javaFormatter = context.getJavaFormatter();
        String target = baseMapper + "<" + name +">";
        Interface mapperInterface = new Interface(mapperPackage + "." + name + "Mapper");
        mapperInterface.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(target);
        mapperInterface.addSuperInterface(daoSuperType);
        GeneratedJavaFile mapper = new GeneratedJavaFile(mapperInterface, targetProject, javaFormatter);
        mapperInterface.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(entityPackage + "." + name));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(baseMapper));
        mapperInterface.addAnnotation("@Mapper");
        mapperJavaFiles.add(mapper);
        }
        return mapperJavaFiles;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

}
