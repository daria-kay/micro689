package com.darakay.micro689.services;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.exception.RecordNotFoundException;
import com.darakay.micro689.mapper.BlackListRecordMapper;
import com.darakay.micro689.repo.MyBatisRecordRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BlackListRecordService {
    private RecordsRepository recordsRepository;
    private MyBatisRecordRepository myBatisRecordRepository;
    private Map<String, BlockRecordService> services;
    private CSVFileReader csvFileReader;

    public BlackListRecordService(RecordsRepository recordsRepository,
                                  MyBatisRecordRepository myBatisRecordRepository, Map<String, BlockRecordService> services,
                                  CSVFileReader csvFileReader) {
        this.recordsRepository = recordsRepository;
        this.myBatisRecordRepository = myBatisRecordRepository;
        this.services = services;
        this.csvFileReader = csvFileReader;

    }

    public void storeRecords(int creatorId, MultipartFile file) {
        for (Map<String, String> record : csvFileReader.read(file)){
            storeRecord(creatorId, record);
        }
    }

    public int storeRecord(int creatorId, Map<String, String> values) {
        Map<String, Integer> recordValues = new HashMap<>();
        for(String blockName : services.keySet()){
            recordValues.put(blockName, services.get(blockName).storeRecord(values));
        }
        recordValues.put("creatorId", creatorId);
        return myBatisRecordRepository.insertMapAsRecord(recordValues);
    }

    public FindMatchesResult findMatches(BlackListRecordDTO request){
        if(request.getPartnerId() != null){
            boolean matches = myBatisRecordRepository.findMatchesWithPartnerId(request);
            return FindMatchesResult.gracefull(matches);
        }
        return FindMatchesResult.gracefull(myBatisRecordRepository.findMatches(request));
    }

    public List<BlackListRecordDTO> getRecords(Pageable pageable) {
        return StreamSupport.stream(recordsRepository.findAll(pageable).spliterator(), true)
                .map(BlackListRecordMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRecord(int recordId) {
        recordsRepository.deleteById(recordId);
    }

    public void updateRecord(String blockType, int recordId, Map<String, String> values) {
        Record record = recordsRepository.findById(recordId)
                .orElseThrow(RecordNotFoundException::new);
        BlockRecordService storage = services.get(blockType);
        storage.updateRecord(getBlockId(record, blockType), values);
    }

    private int getBlockId(Record record, String blockType){
        switch (blockType){
            case "personal-info":
                return record.getPersonalInfo().getId();
            case "passport-info":
                return record.getPassportInfo().getId();
            case "inn":
                return record.getInn().getId();
            case "phone":
                return record.getPhone().getId();
            case "email":
                return record.getEmail().getId();
        }
        throw new BLTypeNotFoundException(blockType);
    }
}
