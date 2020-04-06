package com.haizhi.searches.controller;

import com.haizhi.searches.component.ServerResponse;
import com.haizhi.searches.entity.Item;
import com.haizhi.searches.service.JpaDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "jpa demo控制层")
@RequestMapping("/testJpa")
public class JpaDemoController {

    @Autowired
    private JpaDemoService jpaDemoService;

    @GetMapping("getAllItem")
    @ApiOperation(value = "查询全部")
    public ServerResponse<String> getAllItem() {
        return ServerResponse.success(jpaDemoService.getAllItem());
    }

    @GetMapping("getItemById/{id}")
    @ApiOperation(value = "根据id进行查询")
    public ServerResponse<String> getItemById(@PathVariable Long id) {
        return ServerResponse.success(jpaDemoService.getItemById(id));
    }

    @GetMapping("getItemPage/{name}")
    @ApiOperation(value = "根据id进行查询")
    public ServerResponse<Page<Item>> getItemPage(@PathVariable String name) {
        return ServerResponse.success(jpaDemoService.getItemPage(name));
    }

    @GetMapping("findBySql")
    @ApiOperation(value = "根据id进行查询")
    public ServerResponse<List<Item>> findBySql() {
        return ServerResponse.success(jpaDemoService.findBySql());
    }


    @PostMapping("saveItems")
    @ApiOperation(value = "保存文档")
    public ServerResponse<String> saveItems(@RequestBody List<Item> items) {
        return ServerResponse.success(jpaDemoService.saveItems(items));
    }

    @GetMapping("createIndex")
    @ApiOperation(value = "创建索引及type")
    public ServerResponse<Boolean> createIndex() {
        return ServerResponse.success(jpaDemoService.createIndex());
    }

}
