package com.haizhi.searches;

import com.haizhi.searches.demo.entity.Demo;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.sql.SQLResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单集群演示功能测试用例，spring boot配置项以spring.elasticsearch.bboss开头
 * 对应的配置文件为application.properties文件
 */
@SpringBootTest
public class BBossESStarterTestCase {
    @Test
    void contextLoads() {
    }

    @Test
    public void testDemoQuery(){
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        List<Map> json = clientUtil.sql(Map.class,"{\"query\": \"SELECT * FROM teacher\"}");


        SQLResult<Map> sqlResult = clientUtil.fetchQuery(Map.class, "{\"query\": \"SELECT * FROM teacher\",\"fetch_size\": 1}");

        List<Map> datas = sqlResult.getDatas();
        System.out.println(datas.size());//处理数据
        sqlResult = sqlResult.nextPage();//获取下一页数据
        System.out.println(sqlResult);
//        clientUtil.fetchQuery()

        System.out.println(json);
    }

    @Test
    public void testDemoQuery2(){
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil("esmapper/sql.xml");//初始化一个加载sql配置文件的es客户端接口
        //设置sql查询的参数
        Map params = new HashMap();
        params.put("channelId",1);
        Demo json = clientUtil.sqlObject(Demo.class,"sqlQuery",params);
        System.out.println(json);

    }



}
