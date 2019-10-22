package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.repo.InnBLRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("inn")
public class InnBlackListService extends BaseBlackListService<InnBLRecord> {

    private InnBLRepository innBLRepository;

    public InnBlackListService(InnBLRepository innBLRepository) {
        this.innBLRepository = innBLRepository;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
        innBLRepository.saveAll(parseSCVFile(file, () -> new InnBLRecord(creatorId)));
    }

    @Override
    public int storeRecord(int creatorId, Map<String, String> values) {
        return innBLRepository.save(mapToBlackListRecordType(values, new InnBLRecord(creatorId))).getId();
    }

    @Override
    String[] getFieldsNames() {
        return new String[] {"inn"};
    }
}
