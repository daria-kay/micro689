package com.darakay.micro689.repo;

import com.darakay.micro689.domain.FullFilledBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullFilledBLRepository extends CrudRepository<FullFilledBLRecord, Integer> {
    boolean existsBySurname(String surname);
}
