package com.haizhi.searches.dao;

import com.haizhi.searches.annotation.EsRepository;
import com.haizhi.searches.entity.SqlDemoDoc;

import java.util.Map;

@EsRepository
public interface SqlDemoDao {
    Map<String,Object> testSql();

    SqlDemoDoc paramSql(Map<String,Object> param);

    String paramSqlMap(Map<String, Object> apiName);
}
