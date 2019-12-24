package com.beiming.common.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * mybatis 生成注解,重写CommentGenerator接口
 */
public class MyCommentGenerator extends DefaultCommentGenerator {

    private static final List<String > ids = new ArrayList<>(Arrays.asList("id", "ID", "Id")); //数据库中ID字段的定义

    private Properties properties = new Properties();
    /**
     * 定义属性
     * @param properties 该参数为配置文件中commentGenerator元素定义的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.properties.putAll(properties);
    }

    /**
     * 生成实体类的相关配置
     * @param topLevelClass 用于对生成的实体类操作
     * @param introspectedTable 数据库对应的表
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("formatter", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String remarks = introspectedTable.getRemarks();// 获取表注释
        String tableName = introspectedTable.getTableConfiguration().getTableName();//获取表名
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * Date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addJavaDocLine("");
        topLevelClass.addJavaDocLine("@Builder");
        topLevelClass.addJavaDocLine("@NoArgsConstructor");
        topLevelClass.addJavaDocLine("@AllArgsConstructor");
        topLevelClass.addJavaDocLine("@Data");
        topLevelClass.addJavaDocLine("@Table(name = \"" + tableName + "\")");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty"); //引入@ApiModelProperty
        topLevelClass.addImportedType("lombok.Data"); //引入@Data
        topLevelClass.addImportedType("javax.persistence.Table"); //引入@Table
        topLevelClass.addImportedType("javax.persistence.Id"); //引入@Id
        topLevelClass.addImportedType("javax.persistence.GeneratedValue"); //引入@GeneratedValue
        topLevelClass.addImportedType("javax.persistence.GenerationType"); //引入@GenerationType
        topLevelClass.addImportedType("lombok.Builder"); //引入@Builder
        topLevelClass.addImportedType("lombok.NoArgsConstructor"); //引入@NoArgsConstructor
        topLevelClass.addImportedType("lombok.AllArgsConstructor"); //引入@AllArgsConstructor
    }


    /**
     * 生成实体类中字段的相关配置
     * @param field 用于对生成的实体类属性操作
     * @param introspectedTable 数据库对应的表
     * @param introspectedColumn 数据库对应的表字段
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();  // 获取列注释
        String columnName = introspectedColumn.getActualColumnName();
        if(ids.contains(columnName)){  //如果字段为ID,添加空行,此处只是为了生成的实体类好看
            field.addJavaDocLine("");
            field.addJavaDocLine("@Id");
            field.addJavaDocLine("@GeneratedValue(strategy= GenerationType.IDENTITY)");
        }
        if(StringUtils.isEmpty(remarks)){
            remarks = "ID";
        }
        field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
    }

    /**
     * xml不生成注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {
    }
}
