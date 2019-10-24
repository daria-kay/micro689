package com.darakay.micro689.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BLRecordStorage {
    void storeRecords(int creatorId, MultipartFile file);
    void storeRecord(int creatorId, Map<String, String> values);

}
