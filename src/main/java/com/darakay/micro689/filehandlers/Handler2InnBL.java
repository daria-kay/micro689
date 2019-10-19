package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "inn")
public class Handler2InnBL extends FileHandler {

    @Autowired
    private InnBLRepository innBLRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        innBLRepository.saveAll(StreamSupport.stream(records.spliterator(), true)
        .map(record -> new InnBLRecord(record, 0))
        .collect(Collectors.toList()));
    }
}
