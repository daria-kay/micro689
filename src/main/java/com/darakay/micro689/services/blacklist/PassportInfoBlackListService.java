package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("passport-info")
public class PassportInfoBlackListService extends BaseBlackListService<PassportInfoBLRecord> {

    private PassportInfoBLRepository passportInfoBLRepository;

    public PassportInfoBlackListService(PassportInfoBLRepository passportInfoBLRepository) {
        this.passportInfoBLRepository = passportInfoBLRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        passportInfoBLRepository.saveAll(parseSCVFile(file, () -> new PassportInfoBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        PassportInfoBLRecord record = mapToBlackListRecordType(values, new PassportInfoBLRecord(creatorId));
        return passportInfoBLRepository.save(record).getId();
    }

    @Override
    public String[] getFieldsNames() {
        return new String[] {"passportSeria", "passportNumber"};
    }
}
