package com.darakay.micro689.services;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.springframework.stereotype.Service;

@Service("personal-info")
public class PersonalInfoBlackListService extends BlockRecordService<PersonalInfoBLRecord, PersonalInfoBLRepository> {

    private final String[] CSV_HEADERS = {"surname", "firstName", "secondName", "birthDate"};

    public PersonalInfoBlackListService(PersonalInfoBLRepository personalInfoBLRepository) {
        super(personalInfoBLRepository, PersonalInfoBLRecord::new);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
