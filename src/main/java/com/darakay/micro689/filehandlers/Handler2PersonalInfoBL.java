package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "personal-info")
public class Handler2PersonalInfoBL extends FileHandler {

    @Autowired
    private PersonalInfoBLRepository personalInfoBLRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        personalInfoBLRepository.saveAll(mapCSVRecords(records));
    }

    private List<PersonalInfoBLRecord> mapCSVRecords(Iterable<CSVRecord> records) {
        return StreamSupport.stream(records.spliterator(), true)
                .map(record -> new PersonalInfoBLRecord(record, 0))
                .collect(Collectors.toList());
    }
}
