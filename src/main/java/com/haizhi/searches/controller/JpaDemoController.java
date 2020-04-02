package com.haizhi.searches.controller;

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
    public String getAllItem() {
        return jpaDemoService.getAllItem();
    }

    @GetMapping("getItemById/{id}")
    @ApiOperation(value = "根据id进行查询")
    public String getItemById(@PathVariable Long id) {
        return jpaDemoService.getItemById(id);
    }

    @GetMapping("getItemPage/{name}")
    @ApiOperation(value = "根据id进行查询")
    public Page<Item> getItemPage(@PathVariable String name) {
        return jpaDemoService.getItemPage(name);
    }

    @GetMapping("findBySql")
    @ApiOperation(value = "根据id进行查询")
    public List<Item> findBySql() {
        return jpaDemoService.findBySql();
    }


    @PostMapping("saveItems")
    @ApiOperation(value = "保存文档")
    public String saveItems(@RequestBody List<Item> items) {
        return jpaDemoService.saveItems(items);
    }

    @GetMapping("createIndex")
    @ApiOperation(value = "创建索引及type")
    public boolean createIndex() {
        return jpaDemoService.createIndex();
    }

}
