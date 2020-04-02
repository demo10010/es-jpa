package com.haizhi.searches.controller;

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
    public String addDoc(@RequestBody List<SqlDemoDocQo> docs) {
        return sqlDemoService.addDoc(docs);
    }

    @PostMapping("/delDoc")
    @ApiOperation(value = "删除")
    public Boolean delDoc(@RequestBody SqlDemoDocQo doc) {
        return sqlDemoService.delDoc(doc);
    }

    @PostMapping("/updateDoc")
    @ApiOperation(value = "修改")
    public String updateDoc(@RequestBody List<SqlDemoDocQo> docs) {
        return sqlDemoService.updateDocs(docs);
    }

    @PostMapping("/queryOneDoc")
    @ApiOperation(value = "单条查询")
    public SqlDemoDoc queryOneDoc(@RequestBody SqlDemoDocQo doc) {
        return sqlDemoService.queryOneDoc(doc);
    }

    @PostMapping("/queryMultiDoc")
    @ApiOperation(value = "多条查询")
    public String queryMultiDocs(@RequestBody List<SqlDemoDocQo> docs) {
        return sqlDemoService.queryMultiDocs(docs);
    }

    @PostMapping("/createIndexAndType")
    @ApiOperation(value = "创建索引及type，设置setting和mapping")
    public String createIndexAndType(String indexName) {
        return sqlDemoService.createIndexAndType(indexName);
    }

    @GetMapping("/generateTestData")
    @ApiOperation(value = "生成测试数据")
    public String generateTestData() {
        return sqlDemoService.generateTestData();
    }

}
