package com.darakay.micro689.services;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.services.blacklist.BaseBlackListService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BlackListService {

    private final Map<String, BaseBlackListService> blackListServiceMap;

    public BlackListService(Map<String, BaseBlackListService> blackListServiceMap) {
        this.blackListServiceMap = blackListServiceMap;
    }

    public void handleFile(String blType, MultipartFile multipartFile, int creatorId) {
        getAppropriateBlackListService(blType)
                .storeRecords(creatorId, multipartFile);
    }

    private BaseBlackListService getAppropriateBlackListService(String serviceType) {
        return Optional.ofNullable(blackListServiceMap.get(serviceType))
                .orElseThrow(() -> new BLTypeNotFoundException(serviceType));
    }

    public int addEntry(String blType, Map<String, String> request, int creatorId) {
        return getAppropriateBlackListService(blType).storeRecord(creatorId, request);
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
}


