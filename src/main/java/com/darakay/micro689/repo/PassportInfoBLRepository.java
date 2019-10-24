package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportInfoBLRepository extends BlackListRepository<PassportInfoBLRecord> {
    boolean existsByPassportNumber(String passportNumber);
}
