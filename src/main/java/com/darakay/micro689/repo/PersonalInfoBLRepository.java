package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoBLRepository extends BlackListRepository<PersonalInfoBLRecord> {
    boolean existsBySurname(String surname);
}
