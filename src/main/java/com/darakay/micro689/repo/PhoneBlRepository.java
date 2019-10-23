package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PhoneBLRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBlRepository extends PagingAndSortingRepository<PhoneBLRecord, Integer> {

    boolean existsByPhone(String phone);
}
