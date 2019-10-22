package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.EmailBLRecord;
import com.darakay.micro689.repo.EmailBLRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("email")
public class EmailBlackListService extends BaseBlackListService<EmailBLRecord> {

    private EmailBLRepository emailBLRepository;

    public EmailBlackListService(EmailBLRepository emailBLRepository) {
        this.emailBLRepository = emailBLRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        emailBLRepository.saveAll(parseSCVFile(file, () -> new EmailBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        return emailBLRepository.save(mapToBlackListRecordType(values, new EmailBLRecord(creatorId))).getId();
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"email"};
    }

}
