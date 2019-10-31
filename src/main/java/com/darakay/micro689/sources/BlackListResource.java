package com.darakay.micro689.sources;

import com.darakay.micro689.annotation.ValidRecordId;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.services.BlackListRecordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/v1/black-list")
@CrossOrigin(value = "*", methods = {OPTIONS, GET, POST, PUT})
public class BlackListResource {

    private final BlackListRecordService blackListRecordService;

    public BlackListResource(BlackListRecordService blackListRecordService) {
        this.blackListRecordService = blackListRecordService;
    }

    @PostMapping("/upload-task")
    public ResponseEntity uploadCsvFile(Authentication authentication,
                                        @RequestParam("csv") MultipartFile multipartFile) {
        blackListRecordService.storeRecords(authentication, multipartFile);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/add-entry-task")
    public ResponseEntity addEntry(Authentication authentication,
                                   @RequestBody Map<String, String> request){
        int recordId = blackListRecordService.storeRecord(authentication, request);
        return ResponseEntity.created(URI.create("/api/v1/black-list/" + recordId)).build();
    }

    @PostMapping(value = "/find-matches-task", produces = "application/json;charset=UTF-8")
    public ResponseEntity<FindMatchesResult> findRecords(@Validated @RequestBody FindMatchesRequest request){
        return ResponseEntity.ok(blackListRecordService.findMatches(request));
    }

    @PutMapping("/{black-list-type}/{record-id}")
    public ResponseEntity updateRecord(@PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId,
                                       @RequestBody Map<String, String> values){
        blackListRecordService.updateRecord(blType,recordId, values);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{record-id}")
    public ResponseEntity deleteRecord(@ValidRecordId @PathVariable("record-id") Integer recordId) {
        blackListRecordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BlackListRecordDTO>> getRecords(
            Authentication authentication,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        return ResponseEntity.ok().body(blackListRecordService.getRecords(authentication, PageRequest.of(page, size)));
    }
}
