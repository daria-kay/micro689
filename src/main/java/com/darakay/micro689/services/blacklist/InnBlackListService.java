package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;

@Service("inn")
public class InnBlackListService extends PartialRecordStorage<InnBLRecord, InnBLRepository> {

    private final String[] CSV_HEADERS = {"inn"};

    public InnBlackListService(InnBLRepository innBLRepository, CSVFileReader csvFileReader) {
        super(innBLRepository, InnBLRecord::new, csvFileReader);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
