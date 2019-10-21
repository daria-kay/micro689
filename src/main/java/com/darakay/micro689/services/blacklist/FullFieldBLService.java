package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.exception.InvalidFileFormatException;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.darakay.micro689.utils.MapUtil.convertToSqlDate;

@Service(value = "full-filled")
public class FullFieldBLService extends BaseBLService {

    private final FullFilledBLRepository fullFilledBLRepository;

    public FullFieldBLService(FullFilledBLRepository fullFilledBLRepository) {
        this.fullFilledBLRepository = fullFilledBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        fullFilledBLRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                        .map(record -> map(creatorId, record))
                        .collect(Collectors.toList()));
    }

    private FullFilledBLRecord map(int creatorId, CSVRecord record) {
        if (record.size() != 9)
            throw InvalidFileFormatException.wrongFieldCount(9, record.size());
        return FullFilledBLRecord.builder()
                .creatorId(creatorId)
                .surname(record.get(0))
                .firstName(record.get(1))
                .secondName(record.get(2))
                .birthDate(convertToSqlDate(record.get(3)))
                .passportNumber(record.get(5))
                .passportSeria(record.get(4))
                .inn(record.get(6))
                .phone(record.get(7))
                .email(record.get(8))
                .build();
    }
}
