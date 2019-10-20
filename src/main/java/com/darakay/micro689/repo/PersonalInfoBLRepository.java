package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoBLRepository extends CrudRepository<PersonalInfoBLRecord, Integer> {
    boolean existsBySurname(String surname);
}
