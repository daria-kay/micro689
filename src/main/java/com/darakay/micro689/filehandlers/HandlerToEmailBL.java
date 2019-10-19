package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.repo.EmailBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "email")
public class HandlerToEmailBL extends FileHandler {

    @Autowired
    private EmailBLRepository emailBLRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        emailBLRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                .map(record -> new EmailBLRecord(record, 0))
                .collect(Collectors.toList())
        );
    }
}
