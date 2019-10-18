package com.darakay.micro689.filehandlers;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.filehandlers.FileHandler;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
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
                .map(record -> FullFilledBLRecord.builder()
                        .creatorId(0)
                        .surname(record.get("surname"))
                        .firstName(record.get("firstName"))
                        .secondName(record.get("secondName"))
                        .birthDate(Date.valueOf(record.get("birthDate")))
                        .passportSeria(record.get("passportSer"))
                        .passportNumber(record.get("passportNo"))
                        .inn(record.get("inn"))
                        .phone(record.get("phone"))
                        .email(record.get("email")).build())
                .collect(Collectors.toList());
    }

    @Override
    String[] getCSVColumnNames() {
        return new String[] {"surname", "firstName", "secondName", "birthDate", "passportSer", "passportNo",
        "inn", "phone", "email"};
    }
}
