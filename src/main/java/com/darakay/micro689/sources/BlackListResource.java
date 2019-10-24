package com.darakay.micro689.sources;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesResultDTO;
import com.darakay.micro689.services.BlackListRecordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/black-list")
public class BlackListResource {

    private final BlackListRecordService mainService;

    public BlackListResource(BlackListRecordService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/{black-list-type}/upload-task")
    public ResponseEntity uploadCsvFile(@PathVariable("black-list-type") String blType,
                                        @RequestParam("csv") MultipartFile multipartFile) {

        mainService.handleFile(blType, multipartFile, 0);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/{black-list-type}/add-entry-task")
    public ResponseEntity addEntry(@PathVariable("black-list-type") String blType,
                                   @RequestBody Map<String, String> request){
        mainService.addEntry(blType, request, 0);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/find-matches-task")
    public ResponseEntity<FindMatchesResultDTO> findRecords(@RequestBody Map<String, String> values){
        return ResponseEntity.ok(mainService.findMatches(values));
    }

    @PutMapping("/{black-list-type}/{record-id}")
    public ResponseEntity updateRecord(@PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId,
                                       @RequestBody Map<String, String> values){
        mainService.updateRecord(blType,recordId, values);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{black-list-type}/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId) {
        mainService.deleteRecord(blType, 0, recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{black-list-type}")
    public ResponseEntity<List<BlackListRecordDTO>> getRecords(
            @PathVariable("black-list-type") String blType,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        return ResponseEntity.ok().body(mainService.getRecords(blType, PageRequest.of(page, size)));
    }
}
