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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        if (item.isPresent()){
            Gson gson = new Gson();
            return gson.toJson(item.get());

        }
        return "";
    }


    @GetMapping("getItemPage/{name}")
    @ApiOperation(value = "根据id进行查询")
    public String getItemPage(@PathVariable String name) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPage = itemRepository.findByName(name, pageable);
        Gson gson = new Gson();
        return gson.toJson(itemPage);
    }

}
