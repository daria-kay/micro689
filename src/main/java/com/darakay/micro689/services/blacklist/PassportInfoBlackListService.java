package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;

@Service("passport-info")
public class PassportInfoBlackListService extends PartialRecordStorage<PassportInfoBLRecord, PassportInfoBLRepository> {

    private final String[] CSV_HEADERS = {"passportSeria", "passportNumber"};

    public PassportInfoBlackListService(PassportInfoBLRepository passportInfoBLRepository, CSVFileReader csvFileReader) {
        super(passportInfoBLRepository, PassportInfoBLRecord::new, csvFileReader);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
