package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "phone")
public class Handler2PhoneBL extends FileHandler {

    @Autowired
    private PhoneBlRepository phoneBlRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        phoneBlRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                .map(record -> new PhoneBLRecord(record, 0))
                .collect(Collectors.toList())
        );
    }
}
