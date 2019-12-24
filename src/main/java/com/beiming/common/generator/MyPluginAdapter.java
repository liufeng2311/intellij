package com.beiming.common.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.TableConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PluginAdapter用来指定是否生成指定代码, true表示继续执行其它插件的该方法,false表示不执行其它插件的该方法
 */
public class MyPluginAdapter extends PluginAdapter {

    private static final List<String> ids = new ArrayList<>(Arrays.asList("id", "ID", "Id")); //数据库中ID字段的定义

    private String baseMapper;

    private String mapperPackage;

    private String entityPackage;

    private String controllerPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String targetProject;

    private String basePackage = "user"; //基础模块名

    @Override
    public boolean validate(List<String> list) {
        baseMapper = properties.getProperty("baseMapper");
        mapperPackage = properties.getProperty("mapperPackage");
        entityPackage = properties.getProperty("entityPackage");
        targetProject = properties.getProperty("targetProject");
        controllerPackage = properties.getProperty("controllerPackage");
        servicePackage = properties.getProperty("servicePackage");
        serviceImplPackage = properties.getProperty("serviceImplPackage");
        return true;
    }


    /**
     * 生成Mapper相关配置
     *
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable table) {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        for (TableConfiguration config : context.getTableConfigurations()) {
            String name = config.getDomainObjectName(); //获取生成的实体类名称
            generateMapper(list, name);
            generateController(list, config, name, table);
            generateService(list, name);
            generateServiceImpl(list, name);
        }
        return list;
    }

    /**
     * 生成Mapper层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateMapper(List<GeneratedJavaFile> list, String name) {
        String target = baseMapper + "<" + name + ">";
        Interface mapperInterface = new Interface(mapperPackage + "." + name + "Mapper");
        mapperInterface.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(target);
        mapperInterface.addSuperInterface(daoSuperType);
        GeneratedJavaFile mapper = new GeneratedJavaFile(mapperInterface, targetProject, context.getJavaFormatter());
        mapperInterface.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(entityPackage + "." + name));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(baseMapper));
        mapperInterface.addAnnotation("@Mapper");
        list.add(mapper);
        return list;
    }

    /**
     * 生成Controller层
     */
    public List<GeneratedJavaFile> generateController(List<GeneratedJavaFile> list, TableConfiguration config, String name, IntrospectedTable table) {
        String url = basePackage + "/" + config.getTableName().replace("_", "-");
        TopLevelClass topLevelClass = new TopLevelClass(controllerPackage + "." + name + "Controller");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@Api" + "(tags = \"" + table.getRemarks() + "\")");
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation("@RequestMapping" + "(\"" + url + "\")");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.Api"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.beiming.modules.sys.dict.service.SysDictService"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        Field field = new Field(name + "Service", new FullyQualifiedJavaType(name + "Service"));
        field.addAnnotation("@Autowired");
        field.addJavaDocLine("");
        topLevelClass.addField(field);
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, targetProject, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    /**
     * 生成Service层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateService(List<GeneratedJavaFile> list, String name) {
        Interface service = new Interface(servicePackage + "." + name + "Service");
        service.setVisibility(JavaVisibility.PUBLIC);
        GeneratedJavaFile mapper = new GeneratedJavaFile(service, targetProject, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    /**
     * 生成ServiceImpl层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateServiceImpl(List<GeneratedJavaFile> list, String name) {
        TopLevelClass topLevelClass = new TopLevelClass(serviceImplPackage + "." + name + "ServiceImpl");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(name + "Service");
        topLevelClass.addSuperInterface(daoSuperType);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("AbstractService"));
        topLevelClass.addAnnotation("@Service");
        Field field = new Field(name + "Mapper", new FullyQualifiedJavaType(name + "Mapper"));
        field.addJavaDocLine("");
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.beiming.modules.base.service.AbstractService"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(servicePackage + "." + name + "Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.beiming.modules.sys.dict.mapper.SysDictMapper"));
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, targetProject, context.getJavaFormatter());
        list.add(mapper);
        return list;
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
}
