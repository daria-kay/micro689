package com.darakay.micro689.repo;

import com.darakay.micro689.domain.FullFilledBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullFilledBLRepository extends CrudRepository<FullFilledBLRecord, Integer> {
    List<FullFilledBLRecord> findBySurname(String surname);
}
