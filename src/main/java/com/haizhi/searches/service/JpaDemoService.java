package com.haizhi.searches.service;


import com.haizhi.searches.entity.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JpaDemoService {
    String getAllItem();

    String getItemById( Long id);

    Page<Item> getItemPage(String name);

    List<Item> findBySql();

    String saveItems(List<Item> items);

    boolean createIndex();
}
