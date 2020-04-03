package com.haizhi.searches.service;

import com.haizhi.searches.entity.SqlDemoDoc;
import com.haizhi.searches.entity.SqlDemoDocQo;

import java.util.List;

public interface SqlDemoService {
    String addDoc(List<SqlDemoDocQo> docs);

    Boolean delDoc(SqlDemoDocQo docs);

    String updateDocs(List<SqlDemoDocQo> docs);

    SqlDemoDoc queryOneDoc(SqlDemoDocQo docs);

    String queryByRawSql(String sql);

    String queryMultiDocs(List<SqlDemoDocQo> docs);

    String createIndexAndType(String indexName);

    String generateTestData();

}
