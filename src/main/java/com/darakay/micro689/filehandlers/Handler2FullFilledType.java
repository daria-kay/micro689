package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "full-filled")
public class Handler2FullFilledType extends FileHandler {

    @Autowired
    private FullFilledBLRepository fullFilledBLRepository;

    @Override
    void storeRecords(Iterable<CSVRecord> records) {
        fullFilledBLRepository.saveAll(mapCSVRecordsToDBEntity(records));
    }

    private List<FullFilledBLRecord> mapCSVRecordsToDBEntity(Iterable<CSVRecord> records) {
        return StreamSupport.stream(records.spliterator(), true)
                .map(record -> new FullFilledBLRecord(record, 0))
                .collect(Collectors.toList());
    }
}
