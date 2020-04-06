package com.haizhi.searches.dao;

import com.haizhi.searches.annotation.EsRepository;

import java.util.Map;

@EsRepository
public interface SqlDemoDao {
    Map<String,Object> testSql();

    String paramSql(Map<String,Object> item);

    String paramSqlMap(Map<String, Object> apiName);
}
