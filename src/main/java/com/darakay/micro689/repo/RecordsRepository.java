package com.darakay.micro689.repo;

import com.darakay.micro689.domain.Record;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends PagingAndSortingRepository<Record, Integer> {
}
