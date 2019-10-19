package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        .map(record -> new PassportInfoBLRecord(record, creatorId))
                        .collect(Collectors.toList())
        );
    }
}
