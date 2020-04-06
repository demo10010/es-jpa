package com.haizhi.searches.annotation;

import com.google.gson.Gson;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.*;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.util.io.ClassPathResource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsRepositoryProxyFactoryBean<T> implements FactoryBean {
    //被代理的接口Class对象
    private Class<T> interfaceType;

    private EsRepositoryProxyFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[]{interfaceType}, this::invoke);
    }

    /**
     * @param proxy  被代理的接口
     * @param method 被代理的方法
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if (Object.class.equals(method.getDeclaringClass())) {
//            return method.invoke(this, args);
//        }
        Class<?> returnType = method.getReturnType();

        Configuration configuration = new Configuration();
        //TODO XML文件与接口绑定待处理

        InputStream inputStream = new ClassPathResource("sqlMappers\\sqlDemoMapper.xml").getInputStream();
//            FileInputStream inputStream = new FileInputStream("D:\\");
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, "sqlMappers\\sqlDemoMapper.xml", configuration.getSqlFragments());

        xmlMapperBuilder.parse();
        MappedStatement paramSql = configuration.getMappedStatement(method.getName());//paramSql
        if (paramSql.getResultMaps() == null){
            System.err.println("未配置返回值类型 resultType ");
        }
        Class<?> type = paramSql.getResultMaps().get(0).getType();

        String sql = paramSql.getBoundSql(args).getSql();
        System.out.println(sql);

        ClientInterface restClient = ElasticSearchHelper.getRestClientUtil();
        ESDatas<?> esDatas = restClient.searchList("/_sql", sql, type);//返回的文档封装对象类型
        Gson gson = new Gson();
        String json = gson.toJson(args);// TODO demo处理方式为将传入参数转为json字符串返回
        System.out.println("调用后，结果：{}" + esDatas);
        return esDatas;
    }

    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;
        //配置文件名称
        String resource = "configuration.xml";
        try {
            //使用配置文件构造SqlSessionFactory
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        //2，获取SqlSession
        SqlSession sqlSession = sessionFactory.openSession();

        Collection<MappedStatement> mappedStatements = sqlSession.getConfiguration().getMappedStatements();
        mappedStatements.forEach(mappedStatement -> {
            getSql(mappedStatement,null);
        });


        //3，获取UserDao代理类
//        UserDao userMapper = sqlSession.getMapper(UserDao.class);
//        //4，执行查询
//        User user = userMapper.findUserById(1);
//        Assert.assertNotNull("not find", user);

        return sessionFactory;
    }

    private static String getSql(MappedStatement mappedStatement, Object parameterObject) {
//        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();

        String sql = doGetSql(configuration, boundSql);
        return sql;
    }

    private static String doGetSql(Configuration configuration, BoundSql boundSql) {
        return "";
    }


    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
