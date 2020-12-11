package com.cx.es;

import com.alibaba.fastjson.JSON;
import com.cx.es.entity.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ElasticsearchDemoApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX_NAME_ONE = "chenxin";

    @Test
    void batchAdd() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();

        List<User> list = new ArrayList<>();
        list.add(new User(1L, "test1"));
        list.add(new User(2L, "test2"));
        list.add(new User(3L, "test3"));
        list.add(new User(4L, "test4"));
        list.add(new User(5L, "test5"));

        for (User user : list) {
            bulkRequest.add(new IndexRequest(INDEX_NAME_ONE).source(JSON.toJSONString(user), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }

    @Test
    void searchTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME_ONE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("userName", "test");
        searchSourceBuilder.query(fuzzyQueryBuilder);
        // 60s
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));

        System.out.println("===========================");

        for (SearchHit documentFields : response.getHits()) {
            System.out.println(JSON.toJSONString(documentFields));
        }

    }

}
