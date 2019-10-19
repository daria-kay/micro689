package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportInfoBLRepository extends CrudRepository<PassportInfoBLRecord, Integer> {
    List<PassportInfoBLRecord> findByPassportNumber(String passportNumber);
}
