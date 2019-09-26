# basic


## 1. Features
* 封装基础库baseactivity,basepresenter等
* 配置方式
    //配置下边三个可以实现，路由，注解获取控件，点击事件必须要配置，因为basic中初始化了注解控件
    implementation "com.github.caoyangfei:viewlib:1.1.1.2019_03"
    implementation "com.github.caoyangfei.aptlib:annotation:1.1.1.2019_03"
    annotationProcessor "com.github.caoyangfei.aptlib:complier:1.1.1.2019_03@jar"
    //替换上边的方法
    //项目gradle配置这个插件，然后类似config.gradle中配置版本，版本名不能变，插件中已经固定
    classpath 'com.github.caoyangfei.aptlib:plugin:1.1.1.2019_03'
    //工具类，封装通用工具
    implementation "com.github.caoyangfei:utillib:1.1.1.2019_02"

    /******第三方*******/
    //RXjava
    implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
    implementation "io.reactivex.rxjava2:rxjava:2.2.10"

    //运行时注解必用
    api 'com.google.auto.service:auto-service:1.0-rc4'
    api 'com.squareup:javapoet:1.11.1'
    //切片必用,要是不使用切片，下边的配置也不用配置，建议使用切片，切片可以解决很多侵入性业务，比如方法执行时长检测，检测登陆等
    implementation ""com.github.caoyangfei.aptlib:aop:1.1.1.2019_03"
    api 'org.aspectj:aspectjrt:1.8.14'


## 2. Skills
#### 工具类AND基础Base库
* [Base库](./doc/basic_base.md)

