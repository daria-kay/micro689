package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportInfoBLRepository extends CrudRepository<PassportInfoBLRecord, Integer> {
    boolean existsByPassportNumber(String passportNumber);
}
