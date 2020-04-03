package com.haizhi.searches.component;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EsComponent {

    @Autowired
    private TransportClient transportClient;

    public <T> List<T> queryBySql(String sql, Class<T> resultType) {
        return queryBySql(transportClient,sql,resultType);
    }

    public static <T> List<T> queryBySql(TransportClient tClient, String sql, Class<T> resultType) {
        log.info("\n"+sql);
        //执行sql查询
        try {
            //创建sql查询对象
            SearchDao searchDao = new SearchDao(tClient);
            SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao.explain(sql).explain();
            ActionRequestBuilder builder = select.getBuilder();
            ActionResponse response = select.get();
            if (response instanceof SearchResponse) {
                SearchResponse searchResponse = (SearchResponse) response;
                SearchHit[] hits = searchResponse.getHits().getHits();
                List<T> collect = Arrays.asList(hits).stream().map(hit -> {
                            Gson gson = new Gson();
                            return gson.fromJson(hit.getSourceAsString(), resultType);
                        }
                ).collect(Collectors.toList());
                return collect;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
