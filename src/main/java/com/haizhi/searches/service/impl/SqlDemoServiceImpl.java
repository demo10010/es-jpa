package com.haizhi.searches.service.impl;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.haizhi.searches.dao.SqlDemoDao;
import com.haizhi.searches.entity.Item;
import com.haizhi.searches.entity.SqlDemoDoc;
import com.haizhi.searches.entity.SqlDemoDocQo;
import com.haizhi.searches.service.SqlDemoService;
import lombok.extern.slf4j.Slf4j;
import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private ClientInterface clientInterface;

//    @Autowired
//    private EsComponent esComponent;

    @Autowired
    private SqlDemoDao sqlDemoDao;

    @Override
    public String addDoc(List<SqlDemoDocQo> docs) {
//        clientUtil.addDocument(INDEX_NAME, TYPE_NAME, items.get(0));//单条新增
        String addResult = clientInterface.addDocuments(INDEX_NAME, TYPE_NAME, docs);//多条新增
        log.info(addResult);
        return addResult;
    }

    @Override
    public String generateTestData() {
        IntStream.range(0, 5000).parallel().mapToObj(i ->
                SqlDemoDoc.builder().demoId(String.valueOf(i)).name("name")
                        .orderId(String.valueOf(i)).contrastStatus(i % 3)
                        .contentbody("contentbody" + i).applicationName("applicationName" + i)
                        .agentStarttime(new Date()).agentStarttimezh(new Date())
                        .build()
        ).forEach(doc -> clientInterface.addDocument(INDEX_NAME, TYPE_NAME, doc));
        return "";
    }

    @Override
    public Boolean delDoc(SqlDemoDocQo doc) {
        clientInterface.deleteDocument(INDEX_NAME, TYPE_NAME, doc.getDemoId());
        return null;
    }

    @Override
    public String updateDocs(List<SqlDemoDocQo> docs) {
        String updateDocuments = clientInterface.updateDocuments(INDEX_NAME, TYPE_NAME, docs);
        return updateDocuments;
    }

    /**
     * 基于x-pack sql ,只有一个月试用期，后期需要购买证书
     *
     * @param doc
     * @return
     */
    @Override
    @Deprecated
    public SqlDemoDoc queryOneDoc(SqlDemoDocQo doc) {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        List<Map> json = clientUtil.sql(Map.class, "{\"query\": \"SELECT * FROM demo\"}");
        log.info(json.toString());//打印检索结果
        return null;
    }

    @Override
    public SqlDemoDoc queryBySql(SqlDemoDocQo docs) {
        Item item = new Item();
        item.setName("自定义参数");
        Map<String, Object> param = Maps.newHashMap(BeanMap.create(item));

        SqlDemoDoc map = sqlDemoDao.paramSql(param);

        return null;
    }

    @Override
    public String queryByRawSql(String sql) {
//        List<Object> objects = esComponent.queryBySql(sql, Object.class);
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        //ESDatas包含当前检索的记录集合，最多10条记录，由sql中的limit属性指定
        ESDatas<Map> esDatas = clientUtil.searchList("/_sql",//sql请求
                sql, //elasticsearch-sql支持的sql语句
                Map.class);//返回的文档封装对象类型
        //获取结果对象列表
        List<Map> demos = esDatas.getDatas();

        return esDatas.toString();
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
            log.debug(demoIndice);
        } catch (ElasticSearchException e) {
            e.printStackTrace();
        }

        return null;
    }

}
