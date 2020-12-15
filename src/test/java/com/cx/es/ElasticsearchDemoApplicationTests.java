package com.cx.es;

import com.alibaba.fastjson.JSON;
import com.cx.es.entity.ForumInfoElasticSearchData;
import com.cx.es.entity.UserElasticSearchData;
import com.cx.es.repository.ForumElasticSearchRepository;
import com.cx.es.repository.UserElasticSearchRepository;
import com.cx.es.util.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexBoost;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ElasticsearchDemoApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserElasticSearchRepository userElasticSearchRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ForumElasticSearchRepository forumElasticSearchRepository;

    private static final String INDEX_NAME_ONE = "chenxin";

    private static final String FORUM_INDEX_NAME = "forum_info";

    private static final String INDEX_NAME_JD = "cx_jd_data";

    @Test
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("chenxin");
        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        GetIndexRequest indexRequest = new GetIndexRequest("chenxin");
        boolean exists = restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

    }

    @Test
    void testAdd() throws IOException {
        UserElasticSearchData userElasticSearchData = new UserElasticSearchData("张三", 100.0);
        // 使用客户端操作
        // IndexRequest indexRequest = new IndexRequest("chenxin");
        // indexRequest.source(JSON.toJSON(userElasticSearchData), XContentType.JSON);
        // IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        // System.out.println(response.status());

        // 使用repository操作
        userElasticSearchRepository.save(userElasticSearchData);
    }

    @Test
    void batchAdd() {
        // List<UserElasticSearchData> list = new ArrayList<>();
        // list.add(new UserElasticSearchData("test1", 123.4));
        // list.add(new UserElasticSearchData("test2", 223.4));
        // list.add(new UserElasticSearchData("test3", 323.4));
        // list.add(new UserElasticSearchData("test4", 423.4));
        // list.add(new UserElasticSearchData("test5", 523.4));
        List<ForumInfoElasticSearchData> forumList = new ArrayList<>();
        forumList.add(new ForumInfoElasticSearchData(1L, 1, 1L, "测帖子标题1", "测试帖子内容111", 1L, "自由", 10, 20, 30, new Date(),
            1010L, Arrays.asList("图片1", "图片2")));
        forumList.add(new ForumInfoElasticSearchData(2L, 1, 2L, "测帖子标题2", "测试帖子内容111", 2L, "平等", 20, 20, 30, new Date(),
            1010L, Arrays.asList("图片1", "图片2")));
        forumList.add(new ForumInfoElasticSearchData(1L, 2, 1L, "测短视频标题1", "测试短视频内容111", 3L, "公正", 30, 20, 30,
            new Date(), 1010L, Arrays.asList("图片1", "图片2")));
        forumList.add(new ForumInfoElasticSearchData(2L, 2, 2L, "测短视频标题2", "测试短视频内容222", 4L, "法制", 40, 20, 30,
            new Date(), 1010L, Arrays.asList("图片1", "图片2")));
        forumList.add(new ForumInfoElasticSearchData(3L, 1, 1L, "我和我的祖国", "我和我的祖国，一刻也不能分割", 2L, "平等", 100, 20, 30,
            new Date(), 1010L, Arrays.asList("图片1", "图片2")));
        forumList.add(new ForumInfoElasticSearchData(4L, 1, 2L, "我爱祖国", "爱我中华，啊啊啊啊", 3L, "公正", 60, 20, 30, new Date(),
            1010L, Arrays.asList("图片1", "图片2")));

        // 使用客户端实现
        // BulkRequest bulkRequest = new BulkRequest();
        // for (UserElasticSearchData user : list) {
        // bulkRequest.add(new IndexRequest(INDEX_NAME_ONE).source(JSON.toJSONString(user), XContentType.JSON));
        // }
        // BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        // System.out.println(bulk.status());

        // 使用repository实现
        forumElasticSearchRepository.saveAll(forumList);

    }

    /**
     * 功能描述: Repository基本查询
     *
     * @Author: chenxin
     * @Date: 2020/12/14
     */
    @Test
    public void testRepositoryQuery() {
        // Iterable<UserElasticSearchData> searchDataIterable = userElasticSearchRepository.findAll();
        // searchDataIterable.forEach(System.out::println);

        // 注意分页参数 页码从0开始
        Page<UserElasticSearchData> pageData =
            userElasticSearchRepository.findByUserNameLikeAndBalanceGreaterThan("test", 300.0, PageRequest.of(0, 2));
        System.out.println("pageData.getTotalElements() = " + pageData.getTotalElements());
        System.out.println("dataList = " + pageData.toList());
    }

    @Test
    void searchTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME_JD);
        String keyword = "Java";

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("titleText", keyword));

        // 最大响应时间 60s
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 设置高亮显示
        searchSourceBuilder.highlighter(new HighlightBuilder().field("titleText").requireFieldMatch(false)
            .preTags("<span style='color:red'>").postTags("</span>"));

        // 分页
        searchSourceBuilder.from(1).size(10);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // System.out.println(JSON.toJSONString(response));

        List<Map<String, Object>> result = new ArrayList<>();
        for (SearchHit searchHit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            // 获取高亮搜索结果 替换之前的老结果
            Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
            HighlightField titleText = highlightFieldMap.get("titleText");
            if (titleText != null) {
                sourceAsMap.put("titleText", Arrays.toString(titleText.fragments()));
            }
            result.add(sourceAsMap);
        }
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 功能描述: 复杂查询
     *
     * @Author: chenxin
     * @Date: 2020/12/14
     */
    @Test
    public void testSearch() {
        String keyword = "测试";
        List<Long> ownerIdList = Arrays.asList(2L, 3L);
        Integer ownerType = 1;
        Long modelId = null;
        Long topicId = null;
        Long createUserId = null;
        int page = 1;
        int pageSize = 5;

        NativeSearchQueryBuilder nativeSearchQueryBuilder =
            new NativeSearchQueryBuilder().withIndicesBoost(Arrays.asList(new IndexBoost(FORUM_INDEX_NAME, 1)));
        if (createUserId != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("createUserId", createUserId));
        }
        if (modelId != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("modelId", modelId));
        }
        if (topicId != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("topicId", topicId));
        }
        if (ownerType != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("ownerType", ownerType));
        }

        if (StringUtils.hasLength(keyword)) {
            nativeSearchQueryBuilder
                .withQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title", keyword).boost(5.0f))
                    .should(QueryBuilders.matchQuery("topicName", keyword).boost(5.0f))
                    .should(QueryBuilders.matchQuery("content", keyword)).boost(1.0f));
            // nativeSearchQueryBuilder
            // .withQuery(QueryBuilders.queryStringQuery(keyword).field("title").field("topicName").field("content"));
        }
        // 不查询这些id
        // if (!CollectionUtils.isEmpty(ownerIdList)) {
        // nativeSearchQueryBuilder
        // .withQuery(QueryBuilders.boolQuery().mustNot(QueryBuilders.termsQuery("ownerId", ownerIdList)));
        // }
        NativeSearchQuery searchQuery =
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("likeNum").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("browseNum").order(SortOrder.DESC))
                .withPageable(PageRequest.of(page - 1, pageSize))
                .withHighlightBuilder(new HighlightBuilder().field("title").field("topicName").field("content")
                    .requireFieldMatch(false).preTags("<span style='color:red'>").postTags("</span>"))
                .build();

        SearchHits<ForumInfoElasticSearchData> searchData =
            elasticsearchRestTemplate.search(searchQuery, ForumInfoElasticSearchData.class);
        List<org.springframework.data.elasticsearch.core.SearchHit<ForumInfoElasticSearchData>> searchHits =
            searchData.getSearchHits();
        List<ForumInfoElasticSearchData> dataList = new ArrayList<>();
        for (org.springframework.data.elasticsearch.core.SearchHit<ForumInfoElasticSearchData> searchHit : searchHits) {
            ForumInfoElasticSearchData elasticSearchData = searchHit.getContent();
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();

            // 标题高亮
            List<String> titleList = highlightFields.get("title");
            if (!CollectionUtils.isEmpty(titleList)) {
                elasticSearchData.setTitle(titleList.get(0));
            }

            // 话题高亮
            List<String> topicNameList = highlightFields.get("topicName");
            if (!CollectionUtils.isEmpty(topicNameList)) {
                elasticSearchData.setTopicName(topicNameList.get(0));
            }

            // 内容高亮
            List<String> contentList = highlightFields.get("content");
            if (!CollectionUtils.isEmpty(contentList)) {
                elasticSearchData.setContent(contentList.get(0));
            }

            dataList.add(elasticSearchData);
        }

        System.out.println(JSON.toJSONString(dataList));
    }

    @Test
    public void testDelete() throws IOException {
        // restHighLevelClient.delete(new DeleteRequest(INDEX_NAME_ONE),RequestOptions.DEFAULT);
        UserElasticSearchData userElasticSearchData = new UserElasticSearchData();
        userElasticSearchData.setUserId("1");
        userElasticSearchRepository.delete(userElasticSearchData);
    }

    @Test
    public void testSave() {
        ForumInfoElasticSearchData searchData = forumElasticSearchRepository.findByOwnerIdIsAndOwnerTypeIs(4L, 1);
        searchData.setTitle("统陈新计");
        forumElasticSearchRepository.save(searchData);
    }

    /**
     * 功能描述: 将网页爬取的数据写入es
     *
     * @Author: chenxin
     * @Date: 2020/12/14
     */
    @Test
    public void testGetParseData() throws IOException {
        List<Map<String, String>> mapList = HtmlParseUtil.parseJd("spring");
        System.out.println(JSON.toJSONString(mapList));

        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> map : mapList) {
            bulkRequest.add(new IndexRequest(INDEX_NAME_JD).source(JSON.toJSONString(map), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }

}
