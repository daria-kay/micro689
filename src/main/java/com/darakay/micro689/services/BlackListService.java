package com.darakay.micro689.services;

import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.services.blacklist.BaseBLService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
public class BlackListService {

    private final Map<String, BaseBLService> blackListServiceMap;

    public BlackListService(Map<String, BaseBLService> blackListServiceMap) {
        this.blackListServiceMap = blackListServiceMap;
    }

    public void handleFile(String blType, MultipartFile multipartFile, int creatorId) {
        getAppropriateBlackListService(blType)
                .storeCSVFile(multipartFile, creatorId);
    }

    private BaseBLService getAppropriateBlackListService(String serviceType) {
        return Optional.ofNullable(blackListServiceMap.get(serviceType))
                .orElseThrow(() -> new BLTypeNotFoundException(serviceType));
    }
}


