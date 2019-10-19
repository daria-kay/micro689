package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalInfoBLRepository extends CrudRepository<PersonalInfoBLRecord, Integer> {
    List<PersonalInfoBLRecord> findBySurname(String surname);
}
