package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.repo.EmailBLRepository;
import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;

@Service("email")
public class EmailBlackListService extends PartialRecordStorage<EmailBLRecord, EmailBLRepository> {

    private final String[] CSV_HEADERS = {"email"};

    public EmailBlackListService(EmailBLRepository emailBLRepository, CSVFileReader csvFileReader) {
        super(emailBLRepository, EmailBLRecord::new, csvFileReader);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
