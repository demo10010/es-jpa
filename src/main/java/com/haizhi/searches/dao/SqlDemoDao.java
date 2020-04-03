package com.haizhi.searches.dao;

import com.haizhi.searches.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SqlDemoDao {
    Map<String,Object> testSql();

    String paramSql(Item item);

    String paramSqlMap(Map<String, Object> apiName);
}
