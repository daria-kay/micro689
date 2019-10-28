package com.darakay.micro689.services;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import org.springframework.stereotype.Service;

@Service("phone")
public class PhoneBlackListService extends PartialRecordStorage<PhoneBLRecord, PhoneBlRepository> {

    private final String[] CSV_HEADERS = {"phone"};

    public PhoneBlackListService(PhoneBlRepository phoneBlRepository) {
        super(phoneBlRepository, PhoneBLRecord::new);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
