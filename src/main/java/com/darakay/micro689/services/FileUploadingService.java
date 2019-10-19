package com.darakay.micro689.services;

import com.darakay.micro689.exception.BLTypeNotFoundException;
import com.darakay.micro689.filehandlers.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
public class FileUploadingService {

    @Autowired
    private Map<String, FileHandler> fileHandlerMap;

    public void handleFile(String blType, MultipartFile multipartFile) {
       Optional.ofNullable(fileHandlerMap.get(blType))
                .orElseThrow(() -> new BLTypeNotFoundException(blType))
                .parseAndSave(multipartFile);
    }

}


