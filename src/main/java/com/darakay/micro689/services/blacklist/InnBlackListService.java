package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import org.springframework.stereotype.Service;

@Service("inn")
public class InnBlackListService extends BaseBlackListService<InnBLRecord, InnBLRepository> {

    public InnBlackListService(InnBLRepository innBLRepository) {
        super(innBLRepository, InnBLRecord::new);
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"inn"};
    }
}
