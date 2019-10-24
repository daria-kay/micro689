package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;

@Service("phone")
public class PhoneBlackListService extends PartialRecordStorage<PhoneBLRecord, PhoneBlRepository> {

    private final String[] CSV_HEADERS = {"phone"};

    public PhoneBlackListService(PhoneBlRepository phoneBlRepository, CSVFileReader csvFileReader) {
        super(phoneBlRepository, PhoneBLRecord::new, csvFileReader);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
