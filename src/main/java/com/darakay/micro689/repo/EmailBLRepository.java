package com.darakay.micro689.repo;

import com.darakay.micro689.domain.EmailBLRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailBLRepository extends BlackListRepository<EmailBLRecord> {
    List<EmailBLRecord> findByEmail(String email);
}