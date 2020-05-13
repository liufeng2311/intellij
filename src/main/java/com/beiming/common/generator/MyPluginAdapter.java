package com.beiming.common.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MyPluginAdapter extends PluginAdapter {

    private List<String> DTOIgnore = new ArrayList<>(); //指定DTO生成时忽略哪些字段

    private List<String> VOIgnore = new ArrayList<>(); //指定VO生成时忽略哪些字段

    private List<String> sqlKeyWord = new ArrayList<>(); //指定sql关键字,如果是关键字,通过@Column注解添加别名

    {
        DTOIgnore.add("create_time");
        DTOIgnore.add("create_user");
        DTOIgnore.add("update_time");
        DTOIgnore.add("update_user");
        VOIgnore.add("update_time");
        VOIgnore.add("update_user");
        sqlKeyWord.add("order");
        sqlKeyWord.add("desc");
    }

    private String modulePath; //模块路径

    private String baseMapper; //BaseMapper类全路径

    private String resultModel; //ResultModel类全路径

    private String resultModelShort; //ResultModel类短名称

    private String mapperPackage; //mapper包路径

    private String entityPackage; //entity包路径

    private String dtoPackage; //dto包路径

    private String voPackage; //vo包路径

    private String controllerPackage; //controller包路径

    private String servicePackage; //service包路径

    private String serviceImplPackage; //serviceImpl包路径

    private String abstractService; //serviceImpl包路径

    private String pageDomain; //serviceImpl包路径


    private String javaPath = "src/main/java";

    @Override
    public boolean validate(List<String> list) {
        baseMapper = properties.getProperty("baseMapper");
        resultModel = properties.getProperty("resultModel");
        String[] split = resultModel.split("\\.");
        resultModelShort = split[split.length - 1];
        abstractService = properties.getProperty("abstractService");
        pageDomain = properties.getProperty("pageDomain");
        modulePath = properties.getProperty("modulePath");
        controllerPackage = modulePath + ".controller.";
        servicePackage = modulePath + ".service.";
        serviceImplPackage = modulePath + ".service.impl.";
        mapperPackage = modulePath + ".mapper.";
        entityPackage = modulePath + ".domain.entity.";
        dtoPackage = modulePath + ".domain.dto.";
        voPackage = modulePath + ".domain.vo.";
        return true;
    }


    /**
     * 生成自定义模板
     * controller、service、serviceImpl、mapper、domain
     *
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable table) {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        for (TableConfiguration config : context.getTableConfigurations()) {
            String name = config.getDomainObjectName();     //获取table标签中定义的实体类名称
            //generateDTO(list, config, table);               //生成DTO
            //generateVO(list, config, table);                //生成VO
            //generateQueryDTO(list, config, table);          //生成QueryDTO
            generateMapper(list, name);                     //生成Mapper
            //generateController(list, config, name, table);  //生成Controller
            //generateService(list, name);                    //生成Service
            //generateServiceImpl(list, name);                //生成ServiceImpl
            generateEntity(list, config, table);            //生成Entity
        }
        return list;
    }


    /**
     * 生成Controller层
     */
    public List<GeneratedJavaFile> generateController(List<GeneratedJavaFile> list, TableConfiguration config, String name, IntrospectedTable table) {
        String[] split = modulePath.split("\\.");
        String prefix = split[split.length - 1] + ":";
        TopLevelClass topLevelClass = new TopLevelClass(controllerPackage + name + "Controller");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@Api(tags = \"" + table.getRemarks() + "\")");
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation("@RequestMapping(\"" + config.getTableName().replace("_", "-") + "\")");
        topLevelClass.addAnnotation("//@RequiresRoles({\"" + prefix + config.getTableName().replace("_", "-") + "\"})");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageInfo"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.apache.shiro.authz.annotation.RequiresRoles"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.Api"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiImplicitParam"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.*"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.validation.Valid"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(resultModel));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "QueryDTO"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(voPackage + name + "VO"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(servicePackage + name + "Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "ModifyDTO"));
        packageController(topLevelClass, name, table.getRemarks());
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    //Controller内容拼接
    private void packageController(TopLevelClass topLevelClass, String name, String remark) {
        //属性定义
        String serviceName = lowerFirst(name) + "Service";
        String serviceType = name + "Service";
        String returnType = resultModel;
        String queryDTOName = name + "QueryDTO";
        String modifyDTOName = name + "ModifyDTO";
        String tableName = remark.substring(0, remark.length() - 1); //表注释

        //属性注入
        Field field = new Field(serviceName, new FullyQualifiedJavaType(serviceType));
        field.addAnnotation("@Autowired");
        field.addJavaDocLine("");
        topLevelClass.addField(field);

        //添加list方法
        Method list = new Method("list");
        list.setVisibility(JavaVisibility.PUBLIC);
        list.setReturnType(new FullyQualifiedJavaType(returnType));
        Parameter param1 = new Parameter(new FullyQualifiedJavaType(queryDTOName), "query");
        param1.getAnnotations().add("@Valid");
        param1.getAnnotations().add("@RequestBody");
        list.addParameter(param1);
        list.addBodyLine("PageInfo<" + name + "VO> list = " + serviceName + ".list(query);");
        list.addBodyLine("return " + resultModelShort + ".success(list);");
        list.addAnnotation("@PostMapping(\"list\")");
        list.addAnnotation("@ApiOperation(value = \"" + tableName + "列表\")");
        topLevelClass.addMethod(list);

        //添加insert方法
        Method insert = new Method("insert");
        insert.setVisibility(JavaVisibility.PUBLIC);
        insert.setReturnType(new FullyQualifiedJavaType(returnType));
        Parameter param2 = new Parameter(new FullyQualifiedJavaType(modifyDTOName), "save");
        param2.getAnnotations().add("@Valid");
        param2.getAnnotations().add("@RequestBody");
        insert.addParameter(param2);
        insert.addBodyLine(serviceName + ".insert(save);");
        insert.addBodyLine("return " + resultModelShort + ".success();");
        insert.addAnnotation("@PostMapping(\"insert\")");
        insert.addAnnotation("@ApiOperation(value = \"" + tableName + "新增\")");
        topLevelClass.addMethod(insert);

        //添加update方法
        Method update = new Method("update");
        update.setVisibility(JavaVisibility.PUBLIC);
        update.setReturnType(new FullyQualifiedJavaType(returnType));
        Parameter param3 = new Parameter(new FullyQualifiedJavaType(modifyDTOName), "update");
        param3.getAnnotations().add("@Valid");
        param3.getAnnotations().add("@RequestBody");
        update.addParameter(param3);
        update.addBodyLine(serviceName + ".update(update);");
        update.addBodyLine("return " + resultModelShort + ".success();");
        update.addAnnotation("@PostMapping(\"update\")");
        update.addAnnotation("@ApiOperation(value = \"" + tableName + "修改\")");
        topLevelClass.addMethod(update);

        //添加info方法
        Method info = new Method("info");
        info.setVisibility(JavaVisibility.PUBLIC);
        info.setReturnType(new FullyQualifiedJavaType(returnType));
        Parameter param4 = new Parameter(new FullyQualifiedJavaType("Integer"), "id");
        param4.getAnnotations().add("@PathVariable(\"id\")");
        info.addParameter(param4);
        info.addBodyLine(name + "VO info = " + serviceName + ".info(id);");
        info.addBodyLine("return " + resultModelShort + ".success(info);");
        info.addAnnotation("@GetMapping(\"info/{id}\")");
        info.addAnnotation("@ApiOperation(value = \"" + tableName + "回显\")");
        info.addAnnotation("@ApiImplicitParam(name = \"id\", value = \"id\", required = true)");
        topLevelClass.addMethod(info);

        //添加delete方法
        Method delete = new Method("delete");
        delete.setVisibility(JavaVisibility.PUBLIC);
        delete.setReturnType(new FullyQualifiedJavaType(returnType));
        delete.addParameter(new Parameter(new FullyQualifiedJavaType("List<Integer>"), "delete"));
        delete.addBodyLine(serviceName + ".delete(delete);");
        delete.addBodyLine("return " + resultModelShort + ".success();");
        delete.addAnnotation("@PostMapping(\"delete\")");
        delete.addAnnotation("@ApiOperation(value = \"" + tableName + "删除\")");
        topLevelClass.addMethod(delete);

    }


    /**
     * 生成Service层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateService(List<GeneratedJavaFile> list, String name) {
        Interface service = new Interface(servicePackage + name + "Service");
        service.setVisibility(JavaVisibility.PUBLIC);
        service.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        service.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageInfo"));
        service.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "QueryDTO"));
        service.addImportedType(new FullyQualifiedJavaType(voPackage + name + "VO"));
        service.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "ModifyDTO"));
        packageService(service, name);
        GeneratedJavaFile mapper = new GeneratedJavaFile(service, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    //Service内容拼接
    private void packageService(Interface service, String name) {
        //添加list方法
        Method list = new Method("list");
        list.setVisibility(JavaVisibility.PUBLIC);
        list.setReturnType(new FullyQualifiedJavaType("PageInfo<" + name + "VO>"));
        list.addParameter(new Parameter(new FullyQualifiedJavaType(name + "QueryDTO"), "query"));
        list.setAbstract(true);
        list.addJavaDocLine("");
        list.addJavaDocLine("/**");
        list.addJavaDocLine(" * 列表");
        list.addJavaDocLine(" */");
        service.addMethod(list);

        //添加insert方法
        Method insert = new Method("insert");
        insert.setVisibility(JavaVisibility.PUBLIC);
        insert.addParameter(new Parameter(new FullyQualifiedJavaType(name + "ModifyDTO"), "save"));
        insert.setAbstract(true);
        insert.addJavaDocLine("/**");
        insert.addJavaDocLine(" * 新增");
        insert.addJavaDocLine(" */");
        service.addMethod(insert);

        //添加update方法
        Method update = new Method("update");
        update.setVisibility(JavaVisibility.PUBLIC);
        update.addParameter(new Parameter(new FullyQualifiedJavaType(name + "ModifyDTO"), "update"));
        update.setAbstract(true);
        update.addJavaDocLine("/**");
        update.addJavaDocLine(" * 更新");
        update.addJavaDocLine(" */");
        service.addMethod(update);

        //添加info方法
        Method info = new Method("info");
        info.setVisibility(JavaVisibility.PUBLIC);
        info.setReturnType(new FullyQualifiedJavaType(name + "VO"));
        info.addParameter(new Parameter(new FullyQualifiedJavaType("Integer"), "id"));
        info.setAbstract(true);
        info.addJavaDocLine("/**");
        info.addJavaDocLine(" * 详情");
        info.addJavaDocLine(" */");
        service.addMethod(info);

        //添加delete方法
        Method delete = new Method("delete");
        delete.setVisibility(JavaVisibility.PUBLIC);
        delete.addParameter(new Parameter(new FullyQualifiedJavaType("List<Integer>"), "delete"));
        delete.setAbstract(true);
        delete.addJavaDocLine("/**");
        delete.addJavaDocLine(" * 删除");
        delete.addJavaDocLine(" */");
        service.addMethod(delete);
    }


    /**
     * 生成ServiceImpl层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateServiceImpl(List<GeneratedJavaFile> list, String name) {
        TopLevelClass topLevelClass = new TopLevelClass(serviceImplPackage + name + "ServiceImpl");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(name + "Service");
        topLevelClass.addSuperInterface(daoSuperType);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("AbstractService"));
        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageInfo"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageHelper"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.BeanUtils"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.stream.Collectors;"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(servicePackage + name + "Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "QueryDTO"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(voPackage + name + "VO"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(entityPackage + name));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(mapperPackage + name + "Mapper"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(dtoPackage + name + "ModifyDTO"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(abstractService));
        packageServiceImpl(topLevelClass, name);
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }


    //ServiceImpl内容拼接
    private void packageServiceImpl(TopLevelClass topLevelClass, String name) {
        //属性定义
        String lower = lowerFirst(name);
        String mapperName = lowerFirst(name) + "Mapper";
        String mapperType = name + "Mapper";

        //属性注入
        Field field = new Field(mapperName, new FullyQualifiedJavaType(mapperType));
        field.addJavaDocLine("");
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);

        //添加list方法
        Method list = new Method("list");
        list.setVisibility(JavaVisibility.PUBLIC);
        list.setReturnType(new FullyQualifiedJavaType("PageInfo<" + name + "VO>"));
        list.addParameter(new Parameter(new FullyQualifiedJavaType(name + "QueryDTO"), "query"));
        list.addAnnotation("@Override");
        list.addBodyLine(name + " target = new " + name + "();");
        list.addBodyLine("BeanUtils.copyProperties(query, target);");
        list.addBodyLine("Page<" + name + "VO> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());");
        list.addBodyLine("List<" + name + "> list = " + mapperName + ".select(target);");
        list.addBodyLine("PageInfo<" + name + "VO> pageInfo = new PageInfo<>(page);");
        list.addBodyLine("pageInfo.setList(list.stream().map(x -> dto2vo(x)).collect(Collectors.toList()));");
        list.addBodyLine("return pageInfo;");
        topLevelClass.addMethod(list);

        //添加insert方法
        Method insert = new Method("insert");
        insert.setVisibility(JavaVisibility.PUBLIC);
        insert.addParameter(new Parameter(new FullyQualifiedJavaType(name + "ModifyDTO"), "save"));
        insert.addAnnotation("@Override");
        insert.addBodyLine(name + " target = new " + name + "();");
        insert.addBodyLine("BeanUtils.copyProperties(save, target);");
        insert.addBodyLine(mapperName + ".insert(target);");
        topLevelClass.addMethod(insert);

        //添加update方法
        Method update = new Method("update");
        update.setVisibility(JavaVisibility.PUBLIC);
        update.addParameter(new Parameter(new FullyQualifiedJavaType(name + "ModifyDTO"), "update"));
        update.addAnnotation("@Override");
        update.addBodyLine(name + " target = new " + name + "();");
        update.addBodyLine("BeanUtils.copyProperties(update, target);");
        update.addBodyLine(mapperName + ".updateByPrimaryKeySelective(target);");
        topLevelClass.addMethod(update);

        //添加info方法
        Method info = new Method("info");
        info.setVisibility(JavaVisibility.PUBLIC);
        info.setReturnType(new FullyQualifiedJavaType(name + "VO"));
        info.addParameter(new Parameter(new FullyQualifiedJavaType("Integer"), "id"));
        info.addAnnotation("@Override");
        info.addBodyLine(name + " " + lower + " = " + mapperName + ".selectByPrimaryKey(id);");
        info.addBodyLine(name + "VO " + lower + "VO = dto2vo(" + lower + ");");
        info.addBodyLine("return " + lower + "VO;");
        topLevelClass.addMethod(info);

        //添加delete方法
        Method delete = new Method("delete");
        delete.setVisibility(JavaVisibility.PUBLIC);
        delete.addParameter(new Parameter(new FullyQualifiedJavaType("List<Integer>"), "delete"));
        delete.addAnnotation("@Override");
        delete.addBodyLine("for(Integer id : delete){");
        delete.addBodyLine(mapperName + ".deleteByPrimaryKey(id);");
        delete.addBodyLine("}");
        topLevelClass.addMethod(delete);

        //添加dto2vo
        Method dto2vo = new Method("dto2vo");
        dto2vo.setVisibility(JavaVisibility.PRIVATE);
        dto2vo.setReturnType(new FullyQualifiedJavaType(name + "VO"));
        dto2vo.addParameter(new Parameter(new FullyQualifiedJavaType(name), "source"));
        dto2vo.addBodyLine(name + "VO target = new " + name + "VO();");
        dto2vo.addBodyLine("BeanUtils.copyProperties(source, target);");
        dto2vo.addBodyLine("return target;");
        dto2vo.addJavaDocLine("//DTO转VO");
        topLevelClass.addMethod(dto2vo);
    }


    /**
     * 生成Mapper层
     *
     * @return
     */
    public List<GeneratedJavaFile> generateMapper(List<GeneratedJavaFile> list, String name) {
        String target = baseMapper + "<" + name + ">";
        Interface mapperInterface = new Interface(mapperPackage + name + "Mapper");
        mapperInterface.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(target);
        mapperInterface.addSuperInterface(daoSuperType);
        GeneratedJavaFile mapper = new GeneratedJavaFile(mapperInterface, javaPath, context.getJavaFormatter());
        mapperInterface.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(entityPackage + name));
        mapperInterface.addImportedType(new FullyQualifiedJavaType(baseMapper));
        mapperInterface.addAnnotation("@Mapper");
        list.add(mapper);
        return list;
    }

    /**
     * 生成Entity
     */
    public List<GeneratedJavaFile> generateEntity(List<GeneratedJavaFile> list, TableConfiguration config, IntrospectedTable table) {
        TopLevelClass topLevelClass = new TopLevelClass(entityPackage + config.getDomainObjectName());
        topLevelClass.addField(packageFieldKeyModel(table.getPrimaryKeyColumns().get(0)));
        for (IntrospectedColumn column : table.getBaseColumns()) {
            topLevelClass.addField(packageFieldModel(column, 2));
        }
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@Builder");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Table(name = \"" + table.getTableConfiguration().getTableName() + "\")");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty"); //引入@ApiModelProperty
        topLevelClass.addImportedType("lombok.Data"); //引入@Data
        topLevelClass.addImportedType("javax.persistence.Table"); //引入@Table
        topLevelClass.addImportedType("javax.persistence.Id"); //引入@Id
        topLevelClass.addImportedType("javax.persistence.GeneratedValue"); //引入@GeneratedValue
        topLevelClass.addImportedType("javax.persistence.GenerationType"); //引入@GenerationType
        topLevelClass.addImportedType("javax.persistence.*"); //
        topLevelClass.addImportedType("lombok.Builder"); //引入@Builder
        topLevelClass.addImportedType("java.util.Date"); //引入@Date
        topLevelClass.addImportedType("lombok.NoArgsConstructor"); //引入@NoArgsConstructor
        topLevelClass.addImportedType("lombok.AllArgsConstructor"); //引入@AllArgsConstructor
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    /**
     * 生成DTO
     */
    public List<GeneratedJavaFile> generateDTO(List<GeneratedJavaFile> list, TableConfiguration config, IntrospectedTable table) {
        TopLevelClass topLevelClass = new TopLevelClass(dtoPackage + config.getDomainObjectName() + "ModifyDTO");
        topLevelClass.addField(packageFieldModel(table.getPrimaryKeyColumns().get(0), 0));
        for (IntrospectedColumn column : table.getBaseColumns()) {
            if (DTOIgnore.contains(column.getActualColumnName())) {
                break;
            }
            topLevelClass.addField(packageFieldModel(column, 1));
        }
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@ApiModel(description = \"" + table.getRemarks() + "DTO\")");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel;"); //引入@ApiModel
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty"); //引入@ApiModelProperty
        topLevelClass.addImportedType("lombok.Data"); //引入@Data
        topLevelClass.addImportedType("java.util.Date"); //引入@Builder
        topLevelClass.addImportedType("javax.validation.constraints.*");
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    /**
     * 生成VO
     */
    public List<GeneratedJavaFile> generateVO(List<GeneratedJavaFile> list, TableConfiguration config, IntrospectedTable table) {
        TopLevelClass topLevelClass = new TopLevelClass(voPackage + config.getDomainObjectName() + "VO");
        topLevelClass.addField(packageFieldModel(table.getPrimaryKeyColumns().get(0), 0));
        for (IntrospectedColumn column : table.getBaseColumns()) {
            if (VOIgnore.contains(column.getActualColumnName())) {
                break;
            }
            topLevelClass.addField(packageFieldModel(column, 0));
        }
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@ApiModel(description = \"" + table.getRemarks() + "VO\")");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel;"); //引入@ApiModel
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty"); //引入@ApiModelProperty
        topLevelClass.addImportedType("lombok.Data"); //引入@Data
        topLevelClass.addImportedType("java.util.Date"); //引入@Builder
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    /**
     * 生成QueryDTO
     */
    public List<GeneratedJavaFile> generateQueryDTO(List<GeneratedJavaFile> list, TableConfiguration config, IntrospectedTable table) {
        TopLevelClass topLevelClass = new TopLevelClass(dtoPackage + config.getDomainObjectName() + "QueryDTO");
        topLevelClass.addField(packageFieldModel(table.getPrimaryKeyColumns().get(0), 0));
        topLevelClass.setSuperClass(pageDomain.split("\\.")[pageDomain.split("\\.").length - 1]);
        for (IntrospectedColumn column : table.getBaseColumns()) {
            if (VOIgnore.contains(column.getActualColumnName())) {
                break;
            }
            topLevelClass.addField(packageFieldModel(column, 0));
        }
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@ApiModel(description = \"" + table.getRemarks() + "QueryDTO\")");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel"); //引入@ApiModel
        topLevelClass.addImportedType(pageDomain);
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty"); //引入@ApiModelProperty
        topLevelClass.addImportedType("lombok.Data"); //引入@Data
        GeneratedJavaFile mapper = new GeneratedJavaFile(topLevelClass, javaPath, context.getJavaFormatter());
        list.add(mapper);
        return list;
    }

    //包装实体类非主键属性
    private Field packageFieldModel(IntrospectedColumn column, Integer type) {
        Field field = new Field(column.getJavaProperty(), column.getFullyQualifiedJavaType());
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@ApiModelProperty(value = \"" + column.getRemarks() + "\")");
        if (1 == type) {
            field.addAnnotation("@Max(value = " + column.getLength() + " ,message = \"" + column.getRemarks() + "最大长度为" + column.getLength() + "\")");
            if (!column.isNullable() && column.getFullyQualifiedJavaType().getFullyQualifiedName().equals("java.lang.String")) {
                field.addAnnotation("@NotBlank(message = \"" + column.getRemarks() + "不能为空\")");
            } else if (!column.isNullable() && column.getFullyQualifiedJavaType().getFullyQualifiedName().equals("java.lang.Integer")) {
                field.addAnnotation("@NotNull(message = \"" + column.getRemarks() + "不能为空\")");
            }
        }
        if (2 == type) {
            if (sqlKeyWord.contains(field.getName())) {
                field.addAnnotation("@Column(name = \"`" + field.getName() + "`\")");
            }
        }
        return field;
    }

    //包装实体类主键属性,当type=1时表示生成DTO
    private Field packageFieldKeyModel(IntrospectedColumn column) {
        Field field = new Field(column.getJavaProperty(), column.getFullyQualifiedJavaType());
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("");
        field.addAnnotation("@Id");
        field.addAnnotation("@GeneratedValue(strategy= GenerationType.IDENTITY)");
        field.addAnnotation("@ApiModelProperty(value = \"" + column.getRemarks() + "\")");
        return field;
    }

    //将字符串的首字母小写并返回新的字符串
    private String lowerFirst(String name) {
        String lower = name.substring(0, 1).toLowerCase() + name.substring(1);
        return lower;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

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

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapResultMapWithoutBLOBsElementGenerated(element, introspectedTable);
    }
}
