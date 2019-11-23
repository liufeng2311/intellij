**项目说明**

**项目结构**
├─db  项目数据库表结构SQL语句
│
├─common 公共模块
│  ├─annotation     注解
│  ├─aspect         切面(系统接口日志)
│  ├─constant       常量(Redis前缀,常用正则)
│  ├─enums          枚举(异常定义)
│  ├─exception      异常(自定义异常,统一异常捕获,过滤器异常捕获)
│  |─interceptor    拦截器,作用同切面,建议使用切面
│  |─security       安全框架配置(shiro)
│  |─utils          公共工具类
│ 
├─config 配置信息
│  ├─RedisConfig              redis配置
│  ├─ShiroConfig              shiro配置
│  ├─SwaggerConfig            文档配置
│  ├─TaskExecutorConfig       线程池配置
│  ├─WebMvcConfig             MVC配置
│  ├─
│  |─
│  |─
│  |─
│  |─
├─modules 功能模块
│  ├─task  任务模块
│      |--async            异步任务
│      |--scheduling       定时任务
│  |─sys   系统模块
│      |--dict             字典模块
│      |--user             用户模块
│      |--menu             菜单模块
│      |--role             角色模块
│      |--dept             部门模块
│  |─
│  |─
│  |─
│ 
├─IntellijIdeaJavaApplication 项目启动类
│  
├──resources 
│  ├─mapper SQL对应的XML文件
|  └─application.yml 项目配置文件
|  └─logback-spring.xml 日志配置


**框架选型**
- 核心框架：      SpringBoot
                  |--版本：2.2.1.RELEASE
- 安全框架：      Shiro 
                  |--版本：1.4.1
- 数据库：        MySQL 
                  |--版本：8.0.18
- 持久层框架：    tkMapper
                  |--版本：2.0.2
- 日志框架：      Logback
                  |--版本：2.2.1.RELEASE
- 数据库连接池：  HikariCP
                  |--版本：3.4.1
- 文档框架：      Swagger2
                  |--版本：2.9.2


**Controller模块代码规范**
|---Controller
    |-接口命名：表名
    |-所有不需要权限的操作命名为/anon/接口命名
    |-继承AbstractController,用以获取用户相关信息
|---insert
|   |-请求方式: POST
    |-参数接受方式：@RequestBody
    |-参数效验：@Valid
    |-接口命名：modify
|---delete
|   |-请求方式: GET
    |-参数接收方式：@PathVariable
    |-效验: 
    |-接口命名：del/{param}
|---update
|   |-请求方式: POST
    |-参数接受方式：@RequestBody
    |-参数效验：@Valid
    |-接口命名：modify
|---select
|   |-请求方式: GET
    |-参数接受方式：@RequestBody
    |-参数效验：@Valid
    |-接口命名：info/*/{param}
**账号**