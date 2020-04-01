package com.haizhi.searches.controller;

import com.haizhi.searches.demo.crud.DocumentCRUD;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "bboss demo工程")
@RequestMapping("/testSql")
public class SqlDemoController {

    @Autowired
    private BBossESStarter bbossESStarter;
//
    @Autowired
    private DocumentCRUD documentCRUD;

    @GetMapping("bboss demo工程")
    @ApiOperation(value = "查询全部")
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
        boolean exist = bbossESStarter.getRestClient().existIndiceType("twitter","tweet");

        //判读索引是否存在，false表示不存在，正常返回true表示存在
        exist =  bbossESStarter.getRestClient().existIndice("twitter");

        exist =  bbossESStarter.getRestClient().existIndice("agentinfo");

    }

    public void testPerformaceCRUD() throws Exception {

        //删除/创建文档索引表
        documentCRUD.testCreateIndice();

        documentCRUD.testBulkAddDocuments();
    }
}
