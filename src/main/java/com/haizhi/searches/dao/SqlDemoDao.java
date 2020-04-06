package com.haizhi.searches.dao;

import com.haizhi.searches.annotation.EsRepository;
import com.haizhi.searches.entity.Item;

import java.util.Map;

@EsRepository
public interface SqlDemoDao {
    Map<String,Object> testSql();

    String paramSql(Item item);

    String paramSqlMap(Map<String, Object> apiName);
}
