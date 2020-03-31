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
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Api(description = "mybatis demo控制层")
@RequestMapping("/testBoot")
public class DemoController {
    @Autowired
    private ItemRepository itemRepository;

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
        Stream<Item> item = itemRepository.findItemByBrandAndAndImagesOrName();
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

}
