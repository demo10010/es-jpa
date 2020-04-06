package com.haizhi.searches.annotation;

import com.haizhi.searches.util.EsSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.frameworkset.util.io.ClassPathResource;
import org.frameworkset.util.io.PathMatchingResourcePatternResolver;
import org.frameworkset.util.io.Resource;
import org.frameworkset.util.io.ResourcePatternResolver;
import org.springframework.beans.factory.FactoryBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
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
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        Class<?> returnType = method.getReturnType();
        System.out.println(returnType.getTypeName());
        String methodName = method.getName();
        String methodId = method.getDeclaringClass().getName() + "." + methodName;
        Object result;//参数最多一个，多个可用 Map
        if (args == null) {
            result = doInvoke(methodId, null, returnType);
        } else {
            if (args != null && args.length > 1) {
                throw new Exception("多参数暂不支持,请使用 Map 传入");//TODO 多参数暂不支持,可通过 Map 传入
            } else {
                result = doInvoke(methodId, args[0], returnType);
            }
        }
        return result;
    }

    private static Object doInvoke(String methodId, Object parameterObject, Class<?> returnType) throws Exception {
        InputStream confInput = new org.springframework.core.io.ClassPathResource("mybatis-conf.xml").getInputStream();// TODO 路径修改
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(confInput);
        Configuration configuration = xmlConfigBuilder.parse();

        MappedStatement mappedStatement = configuration.getMappedStatement(methodId);//

        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration mappedStatementConfiguration = mappedStatement.getConfiguration();

        String sql = EsSqlUtil.getSql(mappedStatementConfiguration, boundSql);

        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();//判断操作类型 TODO 目前仅支持查询
        switch (sqlCommandType) {
            case SELECT:
                return EsSqlUtil.doSearch(sql, returnType);
            default:
                throw new RuntimeException("暂不支持的操作类型");
        }
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    private void nnnn() throws IOException {
        Configuration configuration = new Configuration();
        //TODO XML文件与接口绑定待处理

        InputStream inputStream = new ClassPathResource("sqlMappers\\sqlDemoMapper.xml").getInputStream();
//            FileInputStream inputStream = new FileInputStream("D:\\");

//        File file = new File("src/main/resources/properties/test.properties");
//        InputStream in = new FileInputStream(file);
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("mybatis-conf.xml");//  等价于 classpath:*

        for (int i = 0; i < resources.length; i++) {
            File file = resources[i].getFile();
            String uri = resources[i].getURI().getPath();
            String url = resources[i].getURL().getPath();
            String canonicalPath = file.getCanonicalPath();
            String resource = file.getAbsolutePath();
            configuration.addLoadedResource(resource);
        }

        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, "sqlMappers\\sqlDemoMapper.xml", configuration.getSqlFragments());
        xmlMapperBuilder.parse();
    }

}
