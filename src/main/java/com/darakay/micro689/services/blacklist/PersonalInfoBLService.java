package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "personal-info")
public class PersonalInfoBLService extends BaseBLService {

    private final PersonalInfoBLRepository personalInfoBLRepository;

    public PersonalInfoBLService(PersonalInfoBLRepository personalInfoBLRepository) {
        this.personalInfoBLRepository = personalInfoBLRepository;
    }

    @Override
    void storeRecords(Iterable<CSVRecord> records, int creatorId) {
        personalInfoBLRepository.saveAll(StreamSupport.stream(records.spliterator(), true)
                .map(record -> new PersonalInfoBLRecord(record, creatorId))
                .collect(Collectors.toList()));
    }
}
