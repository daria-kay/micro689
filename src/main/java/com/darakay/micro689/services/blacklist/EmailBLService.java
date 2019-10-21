package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.exception.InvalidFileFormatException;
import com.darakay.micro689.repo.EmailBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "email")
public class EmailBLService extends BaseBLService {

    private final EmailBLRepository emailBLRepository;

    public EmailBLService(EmailBLRepository emailBLRepository) {
        this.emailBLRepository = emailBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        emailBLRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                        .map(record -> map(creatorId, record))
                        .collect(Collectors.toList()));
    }

    private EmailBLRecord map(int creatorId, CSVRecord record) {
        if (record.size() != 1)
            throw InvalidFileFormatException.wrongFieldCount(1, record.size());
        return EmailBLRecord.builder()
                .creatorId(creatorId)
                .email(record.get(0))
                .build();
    }

}
