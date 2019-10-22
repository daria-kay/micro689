package com.darakay.micro689.sources;

import com.darakay.micro689.services.BlackListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/black-list")
public class BlackListResource {

    private final BlackListService blackListService;

    public BlackListResource(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @PostMapping("/{black-list-type}/upload-task")
    public ResponseEntity uploadCsvFile(@PathVariable("black-list-type") String blType,
                                        @RequestParam("csv") MultipartFile multipartFile) {

        blackListService.handleFile(blType, multipartFile, 0);
        return ResponseEntity.created(URI.create("/api/v1/black-list/" + blType)).build();
    }

    @PostMapping("/{black-list-type}/add-entry-task")
    public ResponseEntity addEntry(@PathVariable("black-list-type") String blType,
                                   @RequestBody Map<String, String> request){
        int id = blackListService.addEntry(blType, request, 0);
        return ResponseEntity.created(URI.create("/api/v1/black-list/"+blType+"/"+id)).build();
    }
}
