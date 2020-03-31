package com.haizhi.searches.dao;

import com.haizhi.searches.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByTitleAndBrand(String title, Integer brand);

    Page<Item> findByName(String name, Pageable page);

}
