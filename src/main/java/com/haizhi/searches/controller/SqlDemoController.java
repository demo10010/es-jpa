package com.haizhi.searches.controller;

import com.haizhi.searches.component.ServerResponse;
import com.haizhi.searches.entity.SqlDemoDoc;
import com.haizhi.searches.entity.SqlDemoDocQo;
import com.haizhi.searches.service.SqlDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "bboss demo工程")
@RequestMapping("/testSql")
public class SqlDemoController {
    @Autowired
    private SqlDemoService sqlDemoService;

    @PostMapping("/addDoc")
    @ApiOperation(value = "新增")
    public ServerResponse<String> addDoc(@RequestBody List<SqlDemoDocQo> docs) {
        return ServerResponse.success(sqlDemoService.addDoc(docs));
    }

    @PostMapping("/delDoc")
    @ApiOperation(value = "删除")
    public ServerResponse<Boolean> delDoc(@RequestBody SqlDemoDocQo doc) {
        return ServerResponse.success(sqlDemoService.delDoc(doc));
    }

    @PostMapping("/updateDoc")
    @ApiOperation(value = "修改")
    public ServerResponse<String> updateDoc(@RequestBody List<SqlDemoDocQo> docs) {
        return ServerResponse.success(sqlDemoService.updateDocs(docs));
    }

    @PostMapping("/queryBySql")
    @ApiOperation(value = "单条查询")
    public ServerResponse<SqlDemoDoc> queryBySql(@RequestBody SqlDemoDocQo doc) {
        return ServerResponse.success(sqlDemoService.queryBySql(doc));
    }

    @PostMapping("/queryByRawSql")
    @ApiOperation(value = "单条查询")
    public ServerResponse<String> queryByRawSql(@RequestBody String sql) {
        return ServerResponse.success(sqlDemoService.queryByRawSql(sql));
    }

    @PostMapping("/queryOneDoc")
    @ApiOperation(value = "单条查询")
    public ServerResponse<SqlDemoDoc> queryOneDoc(@RequestBody SqlDemoDocQo doc) {
        return ServerResponse.success(sqlDemoService.queryOneDoc(doc));
    }

    @PostMapping("/queryMultiDoc")
    @ApiOperation(value = "多条查询")
    public ServerResponse<String> queryMultiDocs(@RequestBody List<SqlDemoDocQo> docs) {
        return ServerResponse.success(sqlDemoService.queryMultiDocs(docs));
    }

    @PostMapping("/createIndexAndType")
    @ApiOperation(value = "创建索引及type，设置setting和mapping")
    public ServerResponse<String> createIndexAndType(String indexName) {
        return ServerResponse.success(sqlDemoService.createIndexAndType(indexName));
    }

    @GetMapping("/generateTestData")
    @ApiOperation(value = "生成测试数据")
    public ServerResponse<String> generateTestData() {
        return ServerResponse.success(sqlDemoService.generateTestData());
    }

}
