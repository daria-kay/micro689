package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "full-filled")
public class FullFieldBLService extends BaseBLService {

    private final FullFilledBLRepository fullFilledBLRepository;

    public FullFieldBLService(FullFilledBLRepository fullFilledBLRepository) {
        this.fullFilledBLRepository = fullFilledBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        fullFilledBLRepository.saveAll(StreamSupport.stream(records.spliterator(), true)
                .map(record -> new FullFilledBLRecord(record, creatorId))
                .collect(Collectors.toList()));
    }
}
