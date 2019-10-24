package com.darakay.micro689.repo;

import com.darakay.micro689.domain.PhoneBLRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBlRepository extends BlackListRepository<PhoneBLRecord> {

    boolean existsByPhone(String phone);
}
