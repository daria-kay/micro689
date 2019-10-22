package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.springframework.stereotype.Service;

@Service("personal-info")
public class PersonalInfoBlackListService extends BaseBlackListService<PersonalInfoBLRecord, PersonalInfoBLRepository> {

    public PersonalInfoBlackListService(PersonalInfoBLRepository personalInfoBLRepository) {
        super(personalInfoBLRepository, PersonalInfoBLRecord::new);
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"surname", "firstName", "secondName", "birthDate"};
    }
}
