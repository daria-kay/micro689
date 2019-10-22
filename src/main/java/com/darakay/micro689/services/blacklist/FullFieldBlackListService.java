package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.FullFilledBLRecord;
import com.darakay.micro689.repo.FullFilledBLRepository;
import org.springframework.stereotype.Service;

@Service(value = "full-filled")
public class FullFieldBlackListService extends BaseBlackListService<FullFilledBLRecord, FullFilledBLRepository> {


    public FullFieldBlackListService(FullFilledBLRepository fullFilledBLRepository) {
        super(fullFilledBLRepository, FullFilledBLRecord::new);
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"surname", "firstName", "secondName", "birthDate", "passportSeria", "passportNumber",
        "inn", "phone", "email"};
    }
}
