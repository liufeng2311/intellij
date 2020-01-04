一、项目说明

    用于初始化项目,不涉及任何业务逻辑
   
二、项目结构

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


三、框架选型

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


四、代码规范

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
   
   
五、项目打包

        1.依赖jar包分离
            打jar包时将所有的依赖jar包存放在与项目jar包同级的lib目录下,发布至linux服务器时,需要将该目录放置于jar包同目录
            打war包时不支持分离,需要将分离的配置删除
        
        2.打jar包(默认打包方式)
            mvn package                                不跳过测试打jar包
            mvn package -Dmaven.test.skip=true         跳过测试打jar包
            
        3.打war包
            1.pom.xml中修改打包方式为war
            2.pom.xml排除tomcat依赖,设置如下
                <scope>provided</scope>
            3.启动类继承SpringBootServletInitializer并重写configure方法
                @Override
                protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
                    return builder.sources(IntellijIdeaJavaApplication.class);
                } 
            4. mvn package                                不跳过测试打war包
               mvn package -Dmaven.test.skip=true         跳过测试打war包
        4.Linux二进制文件
            1.<executable>true</executable>                  maven插件中设置该属性为true,表示打包为可执行文件
            
六、项目发布(jar发布)
   
    1.jar发布(发布后不是后台运行)
        java -jar /xxx.jar                               非后台发布,不可执行其它操作,否则服务关闭
        java -jar /xxx.jar &                             &表示后台发布
    2.war发布
        放入tomcat的webapps下即可
        访问时默认前缀为项目名,也可用如下方法定义
        server.xml中的<Host>标签下配置如下(path表示8080后的定一层路径,docBase表示war名,reloadable表示war更新后尝试自动重启)
        <Context path="/xxx" docBase="D:/xxxx.war" reloadable="true" />   
    3.可执行文件发布(发布后不是后台运行)
        chmod u+x xxx.jar                                修改权限为可执行
        ./xxx.jar         
        ./xxx.jar   &                                    & 表示后台发布          
    4.注册为系统服务发布(发布后为后台运行)
        1./etc/systemd/system                            该目录下设置系统服务
        2.touch xxx.service                              xxx表示服务的名字
        3.文件内容如下(ExecStart指定要执行的命令)
            [Unit]
            Description=myjar
            After=syslog.target
            
            [Service]
            ExecStart = /usr/bin/java -jar /home/liufeng/test/demo-0.0.1-SNAPSHOT.jar 
                      
            [Install]
            WantedBy=multi-user.target
        4.systemctl start xxx                           启动服务
        5.systemctl stop  xxx                           关闭服务
        6.journalctl -u   XXX                           查看服务日志
    5.docker发布
        1.编写Dockerfile文件内容如下:
            FROM williamyeh/java8                                                基础镜像,使用java8
            MAINTAINER liufeng2311@163.com                                       维护者信息
            WORKDIR /data/demo                                                   容器中的目录,不设置默认在根目录中操作
            ADD demo-0.0.1-SNAPSHOT.jar demo.jar                                 添加文件至上述指定目录
            ADD lib lib                                                          添加文件夹至上述指定目录,可以直接复制文件夹
            EXPOSE 8888                                                          容器对外开放的端口,需要和项目启动的端口一致,启动时指定映射端口
            VOLUME /data/data                                                    指定挂载卷,启动镜像时通过-v参数将该路径的内容映射到指定的宿主机路径
            ENTRYPOINT ["java", "-jar", "demo.jar", ">", "java.log", "&"]        执行的命令
        2.构建Docker镜像
            docker build -t xxx .                                                xxx表示镜像的名字,.表示Dockerfile文件在当前路径下
        3.启动镜像,生成容器
            docker run -d --name containerName -p 8888:8888  -v host-path:container-path imageID   containerName表示生成的容器名,imageID表示镜像ID
            -d表示后台运行容器,同时返回ID
            -p端口映射关系
            -v挂载关系
        4.进入docker容器
            docker exec -it xxx  /bin/bash                                       xxx表示容器ID
         
        
    
    
    
