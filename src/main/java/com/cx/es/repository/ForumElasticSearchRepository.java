package com.cx.es.repository;

import com.cx.es.entity.ForumInfoElasticSearchData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ForumElasticSearchRepository extends ElasticsearchRepository<ForumInfoElasticSearchData, String> {

    ForumInfoElasticSearchData findByOwnerIdIsAndOwnerTypeIs(Long ownerId, Integer ownerType);

}
