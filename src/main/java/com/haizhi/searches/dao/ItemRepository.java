package com.haizhi.searches.dao;

import com.haizhi.searches.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByTitleAndBrand(String title, Integer brand);

    Page<Item> findByName(String name, Pageable page);

    //TODO  需编写DSL
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"title\" : \"?0\"}}}}")
    Page<Item> findByNamePage(String title, Pageable page);

    Stream<Item> findItemByBrandAndImagesOrName(String brand,String images,String name);//JAVA8流

}
