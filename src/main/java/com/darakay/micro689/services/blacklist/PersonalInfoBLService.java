package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.exception.InvalidFileFormatException;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.darakay.micro689.utils.MapUtil.convertToSqlDate;

@Service(value = "personal-info")
public class PersonalInfoBLService extends BaseBLService {

    private final PersonalInfoBLRepository personalInfoBLRepository;

    public PersonalInfoBLService(PersonalInfoBLRepository personalInfoBLRepository) {
        this.personalInfoBLRepository = personalInfoBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        personalInfoBLRepository.saveAll(
                StreamSupport.stream(records.spliterator(), true)
                        .map(record -> map(record, creatorId))
                        .collect(Collectors.toList()));
    }

    private PersonalInfoBLRecord map(CSVRecord record, int creatorId) {
        if (record.size() != 4)
            throw InvalidFileFormatException.wrongFieldCount(4, record.size());
        return PersonalInfoBLRecord.builder()
                .creatorId(creatorId)
                .surname(record.get(0))
                .firstName(record.get(1))
                .secondName(record.get(2))
                .birthDate(convertToSqlDate(record.get(3)))
                .build();
    }
}
