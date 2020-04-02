package com.haizhi.searches.service.impl;

import com.google.gson.Gson;
import com.haizhi.searches.dao.ItemRepository;
import com.haizhi.searches.entity.Item;
import com.haizhi.searches.service.JpaDemoService;
import joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JpaDemoServiceImpl implements JpaDemoService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public String getAllItem() {
        Iterable<Item> all = itemRepository.findAll();
//        itemRepository.searchSimilar()
        Gson gson = new Gson();
        return gson.toJson(all);
    }

    @Override
    public String getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            Gson gson = new Gson();
            return gson.toJson(item.get());
        }
        return Strings.EMPTY;
    }

    @Override
    public Page<Item> getItemPage(String name) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPage = itemRepository.findByName(name, pageable);
        return itemPage;
    }

    @Override
    public List<Item> findBySql() {
        Stream<Item> item = itemRepository.findItemByBrandAndImagesOrName("brand","","");
        List<Item> collect = item.collect(Collectors.toList());
        return collect;
    }

    @Override
    public String saveItems(List<Item> items) {
        items.forEach(item -> itemRepository.save(item));
        return Strings.EMPTY;
    }

    @Override
    public boolean createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Item.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        boolean success = elasticsearchTemplate.putMapping(Item.class);
        return success;
    }
}
