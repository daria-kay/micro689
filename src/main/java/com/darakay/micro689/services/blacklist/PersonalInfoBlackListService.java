package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("personal-info")
public class PersonalInfoBlackListService extends BaseBlackListService<PersonalInfoBLRecord> {

    private PersonalInfoBLRepository personalInfoBLRepository;

    public PersonalInfoBlackListService(PersonalInfoBLRepository personalInfoBLRepository) {
        this.personalInfoBLRepository = personalInfoBLRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        personalInfoBLRepository.saveAll(parseSCVFile(file, () -> new PersonalInfoBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        PersonalInfoBLRecord record = mapToBlackListRecordType(values, new PersonalInfoBLRecord(creatorId));
        return personalInfoBLRepository.save(record).getId();
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"surname", "firstName", "secondName", "birthDate"};
    }
}
