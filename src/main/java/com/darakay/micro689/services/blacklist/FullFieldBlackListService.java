package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service(value = "full-filled")
public class FullFieldBlackListService extends BaseBlackListService<FullFilledBLRecord> {

    private final FullFilledBLRepository fullFilledBLRepository;

    public FullFieldBlackListService(FullFilledBLRepository fullFilledBLRepository) {
        this.fullFilledBLRepository = fullFilledBLRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        fullFilledBLRepository.saveAll(parseSCVFile(file, () -> new FullFilledBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        FullFilledBLRecord record =mapToBlackListRecordType(values, new FullFilledBLRecord(creatorId));
        return fullFilledBLRepository.save(record).getId();
    }


    @Override
    String[] getFieldsNames() {
        return new String[] {"surname", "firstName", "secondName", "birthDate", "passportSeria", "passportNumber",
        "inn", "phone", "email"};
    }
}
