### 1.ES集群需安装elasticsearch-sql插件,链接如下
https://github.com/NLPchina/elasticsearch-sql
### 2.简单的增删改查使用es-jpa，复杂的增删改使用bboss客户端，查询可使用@EsRepository注解
### 3.已集成swagger、knife4j、bboss-elasticsearch、spring-data-elasticsearch(jpa)、loogback日志
### 4.已封装：基于mybatis解析xml + 自定义注解及动态代理 + bboss-elasticsearch的的rest客户端执行sql查询
#####  4.1定义接口，并添加注解 @EsRepository
#####  4.2在 resources\sqlMappers 目录下编写对应xml文件，基于mybatis语法，
#####  4.3在 mybatis-conf.xml 添加对应的XML文件(mapper)的路径
### 5.BBOSS教程见 https://esdoc.bbossgroups.com/
### 6.ES-JPA官方文档 https://docs.spring.io/spring-data/elasticsearch/docs/3.2.6.RELEASE/api/