package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.repo.EmailBLRepository;
import org.springframework.stereotype.Service;

@Service("email")
public class EmailBlackListService extends BaseBlackListService<EmailBLRecord, EmailBLRepository> {

    public EmailBlackListService(EmailBLRepository emailBLRepository) {
        super(emailBLRepository, EmailBLRecord::new);
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"email"};
    }
}
