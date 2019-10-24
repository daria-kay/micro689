package com.darakay.micro689.services;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.TaskResultDTO;
import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.services.blacklist.BLRecordStorage;
import com.darakay.micro689.services.blacklist.PartialRecordStorage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MainService {

    private final Map<String, BLRecordStorage> recordsStorage;
    private final Map<String, PartialRecordStorage> blackListsServices;

    public MainService(Map<String, BLRecordStorage> blackListServiceMap, Map<String, PartialRecordStorage> blackListsServices) {
        this.recordsStorage = blackListServiceMap;
        this.blackListsServices = blackListsServices;
    }

    public void handleFile(String blType, MultipartFile multipartFile, int creatorId) {
       getAppropriateRecordStorage(blType).storeRecords(creatorId, multipartFile);    }


    public void addEntry(String blType, Map<String, String> request, int creatorId) {
        getAppropriateRecordStorage(blType).storeRecord(creatorId, request);
    }

    public void updateRecord(String blType, int recordId, Map<String, String> values) {
        getAppropriateBlackListService(blType).updateRecord(recordId, values);
    }

    public void deleteRecord(String blType, int creatorId, int recordId) {
        getAppropriateBlackListService(blType).deleteRecord(recordId);
    }

    public List<BlackListRecordDTO> getRecords(String blType, Pageable pageable) {
        return getAppropriateBlackListService(blType).getRecords(pageable);
    }

    public TaskResultDTO checkRecordExist(Map<String, String> values) {
        return findMatches(values);
    }

    private TaskResultDTO findMatches(Map<String, String> values) {
        boolean generalResult = blackListsServices.values().stream()
                .filter(blackList -> blackList.canHandle(values.keySet()))
                .map(baseBlackListService -> baseBlackListService.existRecord(values))
                .anyMatch(result -> result.equals(true));
        return TaskResultDTO.grasefull(generalResult);
    }

    private BLRecordStorage getAppropriateRecordStorage(String serviceType) {
        return Optional.ofNullable(recordsStorage.get(serviceType))
                .orElseThrow(() -> new BLTypeNotFoundException(serviceType));
    }

    private PartialRecordStorage getAppropriateBlackListService(String serviceType){
        return Optional.ofNullable(blackListsServices.get(serviceType))
                .orElseThrow(() -> new BLTypeNotFoundException(serviceType));
    }
}


