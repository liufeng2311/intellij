**项目说明**

**项目结构**
│
├─common 公共模块
│  ├─annotation                              注解
│  │     ├─aspect                            切面注解
│  │     ├─param                             自定义参数验证注解和逻辑
│  ├─aspect                                  切面注解逻辑
│  ├─constant                                常量定义
│  ├─enums                                   枚举定义
│  ├─exception                               异常处理(自定义异常、统一异常处理、其他异常处理)
│  ├─generator                               通用Mapper自动生成工具
│  ├─interceptor                             拦截器
│        ├─InsertAndUpdateInterceptor        SQL新增和修改拦截器
│  ├─security                                安全框架配置(shiro)
│  ├─utils                                   公共工具类
│ 
├─config 配置信息
│  ├─RedisConfig              redis配置
│  ├─ShiroConfig              shiro配置
│  ├─SwaggerConfig            swagger配置
│  ├─TaskExecutorConfig       线程池配置
│  ├─ValidatorConfig          参数验证配置
│  ├─WebMvcConfig             MVC配置
│
├─modules 任务
│  ├─base             具体功能公用属性
│  ├─domain           公用实体类
│  ├─log              日志输出
│  ├─mapper           Mapper父类 
│  ├─service          公用service
|
├─task 任务
│  ├─async            异步任务
│  ├─scheduling       定时任务
│ 
├─IntellijIdeaJavaApplication 项目启动类
│  
├─resources 
│  ├─generator           mybatis自动生成配置文件
│  ├─mapper              SQL对应的XML文件
│  ├─application.yml     项目配置文件
│  ├─logback-spring.xml  日志配置


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
|---insert
|   |-请求方式: POST
    |-参数接受方式：@RequestBody
    |-参数效验：@Valid
    |-接口命名：modify
|---delete
|   |-请求方式: GET
    |-参数接收方式：@RequestBody
    |-效验: 
    |-接口命名：del
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