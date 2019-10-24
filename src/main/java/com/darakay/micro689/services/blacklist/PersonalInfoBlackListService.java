package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;

@Service("personal-info")
public class PersonalInfoBlackListService extends PartialRecordStorage<PersonalInfoBLRecord, PersonalInfoBLRepository> {

    private final String[] CSV_HEADERS = {"surname", "firstName", "secondName", "birthDate"};

    public PersonalInfoBlackListService(PersonalInfoBLRepository personalInfoBLRepository, CSVFileReader csvFileReader) {
        super(personalInfoBLRepository, PersonalInfoBLRecord::new, csvFileReader);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
