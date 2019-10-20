package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PhoneBLRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBlRepository extends CrudRepository<PhoneBLRecord, Integer> {

    boolean existsByPhone(String phone);
}
