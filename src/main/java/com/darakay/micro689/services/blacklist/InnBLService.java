package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "inn")
public class InnBLService extends BaseBLService {

    private final InnBLRepository innBLRepository;

    public InnBLService(InnBLRepository innBLRepository) {
        this.innBLRepository = innBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        innBLRepository.saveAll(StreamSupport.stream(records.spliterator(), true)
        .map(record -> new InnBLRecord(record, creatorId))
        .collect(Collectors.toList()));
    }
}
