package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoBLRepository extends PagingAndSortingRepository<PersonalInfoBLRecord, Integer> {
    boolean existsBySurname(String surname);
}
