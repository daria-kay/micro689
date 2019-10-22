package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PhoneBLRecord;
import com.darakay.micro689.repo.PhoneBlRepository;
import org.springframework.stereotype.Service;

@Service("phone")
public class PhoneBlackListService extends BaseBlackListService<PhoneBLRecord, PhoneBlRepository> {

    public PhoneBlackListService(PhoneBlRepository phoneBlRepository) {
        super(phoneBlRepository, PhoneBLRecord::new);
    }

    @Override
    String[] getFieldsNames() {
        return new String[]{"phone"};
    }
}
