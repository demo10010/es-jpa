package com.haizhi.searches.dao;

import com.haizhi.searches.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByTitleAndBrand(String title, Integer brand);

    Page<Item> findByName(String name, Pageable page);

//    ./bin/elasticsearch-plugin install https://github.com/NLPchina/elasticsearch-sql/releases/download/6.8.2.0/elasticsearch-sql-6.8.2.0.zip

//    @Query("select * from item")//TODO  不支持
    Stream<Item> findItemByBrandAndAndImagesOrName();

}
