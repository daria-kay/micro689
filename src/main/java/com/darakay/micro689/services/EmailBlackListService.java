package com.darakay.micro689.services;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.repo.EmailBLRepository;
import org.springframework.stereotype.Service;

@Service("email")
public class EmailBlackListService extends BlockRecordService<EmailBLRecord, EmailBLRepository> {

    private final String[] CSV_HEADERS = {"email"};

    public EmailBlackListService(EmailBLRepository emailBLRepository) {
        super(emailBLRepository, EmailBLRecord::new);
    }

    @Override
    public String[] getCSVHeaders() {
        return CSV_HEADERS;
    }
}
