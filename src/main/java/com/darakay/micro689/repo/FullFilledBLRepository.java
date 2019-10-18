package com.darakay.micro689.repo;

import com.darakay.micro689.domain.FullFilledBLRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FullFilledBLRepository extends CrudRepository<FullFilledBLRecord, Integer> {
    List<FullFilledBLRecord> findBySurname(String surname);
}
