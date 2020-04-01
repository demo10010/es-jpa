package com.haizhi.searches.controller;

import com.google.gson.Gson;
import com.haizhi.searches.dao.ItemRepository;
import com.haizhi.searches.entity.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Api(description = "jpa demo控制层")
@RequestMapping("/testJpa")
public class JpaDemoController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("getAllItem")
    @ApiOperation(value = "查询全部")
    public String getAllItem() {
        Iterable<Item> all = itemRepository.findAll();
//        itemRepository.searchSimilar()
        Gson gson = new Gson();
        return gson.toJson(all);
    }

    @GetMapping("getItemById/{id}")
    @ApiOperation(value = "根据id进行查询")
    public String getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            Gson gson = new Gson();
            return gson.toJson(item.get());

        }
        return "";
    }

    @GetMapping("getItemPage/{name}")
    @ApiOperation(value = "根据id进行查询")
    public Page<Item> getItemPage(@PathVariable String name) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPage = itemRepository.findByName(name, pageable);
        return itemPage;
    }

    @GetMapping("findBySql")
    @ApiOperation(value = "根据id进行查询")
    public List<Item> findBySql() {
        Stream<Item> item = itemRepository.findItemByBrandAndImagesOrName("brand","","");
        List<Item> collect = item.collect(Collectors.toList());
        return collect;
    }


    @PostMapping("saveItems")
    @ApiOperation(value = "根据id进行查询")
    public String getItemById(@RequestBody List<Item> items) {
        Iterator<Item> iterator = items.iterator();
        items.forEach(item -> itemRepository.save(item));
        return "";
    }

    @GetMapping("createIndex")
    @ApiOperation(value = "创建索引及type")
    public boolean createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Item.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        boolean success = elasticsearchTemplate.putMapping(Item.class);
        return success;
    }

}
