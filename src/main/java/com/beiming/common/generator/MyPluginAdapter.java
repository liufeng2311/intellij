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
 * PluginAdapter用来指定是否生成指定代码, true表示继续执行其它插件的该方法,false表示不执行其它插件的该方法
 *
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
     * 不生成SelectByPrimaryKey(默认生成)
     */
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成ExampleWhereClause(默认生成)
     */
    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成SelectByExample(默认生成),不含BLOB属性
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成DeleteByExample(默认生成)
     */
    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成DeleteByPrimaryKey(默认生成)
     */
    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成Insert(默认生成)
     */
    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成InsertSelective(默认生成)
     */
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成BaseColumn
     */
    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成Example类
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
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

}
