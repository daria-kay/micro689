package com.darakay.micro689.repo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface BlackListRepository<BLRecord> extends PagingAndSortingRepository<BLRecord, Integer>,
        QueryByExampleExecutor<BLRecord> {
}
