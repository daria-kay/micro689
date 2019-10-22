package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.springframework.stereotype.Service;

@Service("passport-info")
public class PassportInfoBlackListService extends BaseBlackListService<PassportInfoBLRecord, PassportInfoBLRepository> {

    public PassportInfoBlackListService(PassportInfoBLRepository passportInfoBLRepository) {
        super(passportInfoBLRepository, PassportInfoBLRecord::new);
    }

    @Override
    public String[] getFieldsNames() {
        return new String[] {"passportSeria", "passportNumber"};
    }
}
