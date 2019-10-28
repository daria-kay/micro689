package com.darakay.micro689.services;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import org.springframework.stereotype.Service;

@Service("inn")
public class InnBlackListService extends BlockRecordService<InnBLRecord, InnBLRepository> {

    private final String[] CSV_HEADERS = {"inn"};

    public InnBlackListService(InnBLRepository innBLRepository) {
        super(innBLRepository, InnBLRecord::new);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
