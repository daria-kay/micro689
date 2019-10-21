package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.exception.InvalidFileFormatException;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "passport-info")
public class PassportInfoBLService extends BaseBLService {

    private final PassportInfoBLRepository passportInfoBLRepository;

    public PassportInfoBLService(PassportInfoBLRepository passportInfoBLRepository) {
        this.passportInfoBLRepository = passportInfoBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        passportInfoBLRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                        .map(record -> map(creatorId, record))
                        .collect(Collectors.toList()));
    }

    private PassportInfoBLRecord map(int creatorId, CSVRecord record) {
        if (record.size() != 2)
            throw InvalidFileFormatException.wrongFieldCount(2, record.size());
        return PassportInfoBLRecord.builder()
                .creatorId(creatorId)
                .passportSeria(record.get(0))
                .passportNumber(record.get(1))
                .build();
    }
}
