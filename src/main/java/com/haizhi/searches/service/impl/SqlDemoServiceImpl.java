package com.haizhi.searches.service.impl;

import com.google.gson.Gson;
import com.haizhi.searches.demo.crud.DocumentCRUD;
import com.haizhi.searches.entity.SqlDemoDoc;
import com.haizhi.searches.entity.SqlDemoDocQo;
import com.haizhi.searches.service.SqlDemoService;
import lombok.extern.slf4j.Slf4j;
import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SqlDemoServiceImpl implements SqlDemoService {

    private static final String INDEX_NAME = "demo";
    private static final String TYPE_NAME = "demo";

    @Autowired
    private BBossESStarter bbossESStarter;
    //
    @Autowired
    private DocumentCRUD documentCRUD;

    @Override
    public String addDoc(List<SqlDemoDocQo> docs) {
        //创建创建/修改/获取/删除文档的客户端对象，单实例多线程安全
        ClientInterface clientUtil = bbossESStarter.getRestClient();
//        clientUtil.addDocument(INDEX_NAME, TYPE_NAME, items.get(0));//单条新增
        String addResult = clientUtil.addDocuments(INDEX_NAME, TYPE_NAME, docs);//多条新增
        log.info(addResult);
        return addResult;
    }

    @Override
    public String generateTestData() {
        //生成测试数据,parallel可验证客户端的线程安全性
        ClientInterface clientUtil = bbossESStarter.getRestClient();
        IntStream.range(0, 1000000).parallel().mapToObj(i ->
                SqlDemoDoc.builder().demoId(String.valueOf(i)).name("name")
                        .orderId(String.valueOf(i)).contrastStatus(i % 3)
                        .contentbody("contentbody" + i).applicationName("applicationName" + i)
                        .agentStarttime(new Date()).agentStarttimezh(new Date())
                        .build()
        ).forEach(doc->clientUtil.addDocument(INDEX_NAME, TYPE_NAME, doc));

        return "";
    }

    @Override
    public Boolean delDoc(SqlDemoDocQo doc) {
        ClientInterface clientUtil = bbossESStarter.getRestClient();
        clientUtil.deleteDocument(INDEX_NAME, TYPE_NAME, doc.getDemoId());
        return null;
    }

    @Override
    public String updateDocs(List<SqlDemoDocQo> docs) {
        ClientInterface restClient = bbossESStarter.getRestClient();
        String updateDocuments = restClient.updateDocuments(INDEX_NAME, TYPE_NAME, docs);
        return updateDocuments;
    }

    @Override
    public SqlDemoDoc queryOneDoc(SqlDemoDocQo doc) {
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil("esmapper/demo-sql.xml");//初始化一个加载sql配置文件的es客户端接口
        //设置sql查询的参数
        Map params = new HashMap();
        params.put("channelId", 1);
        params.put("name", "乔丹");
        String json = clientUtil.executeHttp("/_xpack/sql", "sqlQuery", params,
                ClientInterface.HTTP_POST
        );
        System.out.println(json);//打印检索结果
        Gson gson = new Gson();
        SqlDemoDoc sqlDemoDoc = gson.fromJson(json, SqlDemoDoc.class);
        return sqlDemoDoc;
    }

    @Override
    public String queryMultiDocs(List<SqlDemoDocQo> docs) {
        return null;
    }

    @Override
    public String createIndexAndType(String indexName) {
        //创建加载配置文件的客户端工具，单实例多线程安全
        ClientInterface clientUtil = bbossESStarter.getConfigRestClient("esmapper/demo.xml");
        try {
            //判读索引表demo是否存在，存在返回true，不存在返回false
            boolean exist = clientUtil.existIndice(INDEX_NAME);
            //如果索引表demo已经存在先删除mapping
            if (exist) {
                String r = clientUtil.dropIndice(INDEX_NAME);
                log.info("index--[demo] drop result ：", r);
                exist = clientUtil.existIndice(INDEX_NAME);
                log.info("index--[demo] exist ：", exist);
                String demoIndice = clientUtil.getIndice(INDEX_NAME);//获取最新建立的索引表结构
                log.info("index--[demo] struct：", demoIndice);
            }
            //创建索引表demo TODO 使用jpa创建更方便
            clientUtil.createIndiceMapping(INDEX_NAME, "createDemoIndice");//索引表mapping dsl脚本名称，在esmapper/demo.xml中定义createDemoIndice

            String demoIndice = clientUtil.getIndice(INDEX_NAME);//获取最新建立的索引表结构
            System.out.println(demoIndice);
        } catch (ElasticSearchException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String testIndixCurd() throws Exception {
        //判读索引是否存在，false表示不存在，正常返回true表示存在
        boolean existIndice = bbossESStarter.getRestClient().existIndice("twitter");
        boolean existIndiceType = bbossESStarter.getRestClient().existIndiceType("twitter", "tweet");

        //删除/创建文档索引表
        documentCRUD.testCreateIndice();
        //添加/修改单个文档
        documentCRUD.testAddAndUpdateDocument();
        //批量添加文档
        documentCRUD.testBulkAddDocument();
        //检索文档
        documentCRUD.testSearch();
        //批量修改文档
        documentCRUD.testBulkUpdateDocument();

        //检索批量修改后的文档
        documentCRUD.testSearch();
        //带list复杂参数的文档检索操作
        documentCRUD.testSearchArray();
        //带from/size分页操作的文档检索操作
        documentCRUD.testPagineSearch();
        //带sourcefilter的文档检索操作
        documentCRUD.testSearchSourceFilter();

        documentCRUD.updateDemoIndice();
        documentCRUD.testBulkAddDocuments();
        return "";
    }

    public void testBbossESStarter() throws Exception {
        //验证环境,获取es状态
//        String response = serviceApiUtil.restClient().executeHttp("_cluster/state?pretty",ClientInterface.HTTP_GET);

//        System.out.println(response);
        //判断索引类型是否存在，false表示不存在，正常返回true表示存在
        boolean exist = bbossESStarter.getRestClient().existIndiceType("twitter", "tweet");

        //判读索引是否存在，false表示不存在，正常返回true表示存在
        exist = bbossESStarter.getRestClient().existIndice("twitter");

        exist = bbossESStarter.getRestClient().existIndice("agentinfo");

    }

    public void testPerformaceCRUD() throws Exception {

        //删除/创建文档索引表
        documentCRUD.testCreateIndice();

        documentCRUD.testBulkAddDocuments();
    }
}
