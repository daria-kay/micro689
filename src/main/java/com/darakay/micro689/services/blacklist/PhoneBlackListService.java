package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("phone")
public class PhoneBlackListService extends BaseBlackListService<PhoneBLRecord> {

    private PhoneBlRepository phoneBlRepository;

    public PhoneBlackListService(PhoneBlRepository phoneBlRepository) {
        this.phoneBlRepository = phoneBlRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        phoneBlRepository.saveAll(parseSCVFile(file, () -> new PhoneBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        PhoneBLRecord record = mapToBlackListRecordType(values, new PhoneBLRecord(creatorId));
        return phoneBlRepository.save(record).getId();
    }

    @Override
    String[] getFieldsNames() {
        return new String[]{"phone"};
    }
}
