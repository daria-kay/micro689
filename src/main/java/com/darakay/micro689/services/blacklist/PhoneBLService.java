package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "phone")
public class PhoneBLService extends BaseBLService {

    private final PhoneBlRepository phoneBlRepository;

    public PhoneBLService(PhoneBlRepository phoneBlRepository) {
        this.phoneBlRepository = phoneBlRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        phoneBlRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                .map(record -> new PhoneBLRecord(record, creatorId))
                .collect(Collectors.toList())
        );
    }
}
