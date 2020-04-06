package com.haizhi.searches.annotation;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.frameworkset.util.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class MybatisXmlToSql {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        String res = "D:\\sqlDemoMapper.xml";

        try {
            InputStream inputStream = new ClassPathResource("mybatis-conf.xml").getInputStream();
//            FileInputStream inputStream = new FileInputStream("D:\\sqlDemoMapper.xml");
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, res, configuration.getSqlFragments());

            xmlMapperBuilder.parse();
            MappedStatement paramSql = configuration.getMappedStatement("testSql");//paramSql
            String sql = paramSql.getBoundSql(null).getSql();
            System.out.println(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
