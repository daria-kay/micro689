package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "passport-info")
public class Handler2PassportInfoBL extends FileHandler {
    @Autowired
    private PassportInfoBLRepository passportInfoBLRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        passportInfoBLRepository.saveAll(
                mapCSVFile(records)
        );
    }

    private List<PassportInfoBLRecord> mapCSVFile(Iterable<CSVRecord> records) {
        return StreamSupport.stream(records.spliterator(), true)
        .map(record -> new PassportInfoBLRecord(record, 0))
        .collect(Collectors.toList());
    }
}
