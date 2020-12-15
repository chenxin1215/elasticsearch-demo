package com.cx.es.repository;

import com.cx.es.entity.UserElasticSearchData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticSearchRepository extends ElasticsearchRepository<UserElasticSearchData, String> {

    Page<UserElasticSearchData> findByUserNameLikeAndBalanceGreaterThan(String userName, Double balance,
        Pageable pageable);
}
