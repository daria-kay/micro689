package com.darakay.micro689.repo;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsRepository extends PagingAndSortingRepository<Record, Integer> {
    List<Record> findByCreator(User user, Pageable pageable);
}
