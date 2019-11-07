package com.darakay.micro689.services;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.domain.User;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.dto.FindRecordsRequest;
import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.exception.CannotReadFileException;
import com.darakay.micro689.exception.RecordNotFoundException;
import com.darakay.micro689.repo.MyBatisRecordRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public void storeRecords(Authentication auth, MultipartFile file) {
        User creator = ((User)auth.getPrincipal());
        for (Map<String, String> record : csvFileReader.read(file)){
            storeRecord(creator.getId(), record);
        }
    }

    public int storeRecord(Authentication auth, Map<String, String> values) {
        User creator = ((User)auth.getPrincipal());
        return storeRecord(creator.getId(), values);
    }

    private int storeRecord(int creatorId, Map<String, String> values){
        Map<String, Integer> recordValues = new HashMap<>();
        for(String blockName : services.keySet()){
            recordValues.put(blockName, services.get(blockName).storeRecord(values));
        }
        checkRecordsValues(recordValues);
        recordValues.put("creatorId", creatorId);
        return myBatisRecordRepository.insertMapAsRecord(recordValues);
    }

    private void checkRecordsValues(Map<String, Integer> recordValues) {
        if(recordValues.values().stream().noneMatch(Objects::nonNull))
            throw new CannotReadFileException("Отсутствуют заголовки");
    }

    public FindMatchesResult findMatches(FindMatchesRequest request){
        if(request.getPartnerId() != null){
            boolean matches = myBatisRecordRepository
                    .findMatchesWithPartnerId(request.getPartnerId(), request.getExample());
            return FindMatchesResult.gracefull(matches);
        }
        return FindMatchesResult.gracefull(myBatisRecordRepository.findMatches(request.getExample()));
    }

    public List<BlackListRecordDTO> getRecords(Authentication auth, Pageable pageable) {
        User creator = ((User)auth.getPrincipal());
        return recordsRepository.findByCreator(creator, pageable).parallelStream()
                .map(BlackListRecordDTO::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize(value = "@recordAccessEvaluator.recordBelongsToUser(principal.id, #recordId)")
    public void deleteRecord(int recordId) {
        recordsRepository.deleteById(recordId);
    }

    @PreAuthorize(value = "@recordAccessEvaluator.recordBelongsToUser(principal.id, #recordId)")
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

    public List<BlackListRecordDTO> findRecords(FindRecordsRequest request, Authentication auth) {
        User creator = ((User)auth.getPrincipal());
        List<Integer> ids = myBatisRecordRepository.findRecords(creator.getId(), request);
        return ids.parallelStream()
                .map(id -> recordsRepository.findById(id))
                .map(record -> new BlackListRecordDTO(record.orElseThrow(RecordNotFoundException::new)))
                .collect(Collectors.toList());
    }
}
