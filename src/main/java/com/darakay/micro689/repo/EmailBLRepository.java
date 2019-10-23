package com.darakay.micro689.repo;

import com.darakay.micro689.domain.EmailBLRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailBLRepository extends PagingAndSortingRepository<EmailBLRecord, Integer> {
}
