package com.darakay.micro689.sources;

import com.darakay.micro689.services.FileUploadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/black-list")
public class UploadFileResource {

    @Autowired
    private FileUploadingService fileUploadingService;

    @PostMapping("/{black-list-type}/upload-task")
    public ResponseEntity uploadCsvFile(@PathVariable("black-list-type") String blType,
                                        @RequestParam("csv") MultipartFile multipartFile){

        fileUploadingService.handleFile(blType, multipartFile);
        return ResponseEntity.created(URI.create("/api/v1/black-list/"+blType)).build();
    }
}
