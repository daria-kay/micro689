package com.darakay.micro689.sources;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.services.BlackListRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Api("Операции с черными списками")
@RestController
@RequestMapping("/api/v1/black-list")
public class BlackListResource {

    private final BlackListRecordService mainService;

    public BlackListResource(BlackListRecordService mainService) {
        this.mainService = mainService;
    }

    @ApiOperation(value = "Загрузка записей черных списков из csv файла",
            notes = "csv файл без заголовков, с ';' в качестве разделителя полей")
    @PostMapping("/{black-list-type}/upload-task")
    public ResponseEntity uploadCsvFile(@PathVariable("black-list-type") String blType,
                                        @RequestParam("csv") MultipartFile multipartFile) {

        mainService.handleFile(blType, multipartFile, 0);
        return ResponseEntity.created(URI.create("")).build();
    }

    @ApiOperation("Добавление записи в черный список")
    @PostMapping("/{black-list-type}/add-entry-task")
    public ResponseEntity addEntry(@PathVariable("black-list-type") String blType,
                                   @RequestBody Map<String, String> request){
        mainService.addEntry(blType, request, 0);
        return ResponseEntity.created(URI.create("")).build();
    }

    @ApiOperation("Поиск совпадений в черных списках по переданным полям")
    @PostMapping(value = "/find-matches-task", produces = "application/json")
    public ResponseEntity<FindMatchesResult> findRecords(@Validated @RequestBody BlackListRecordDTO request){
        return ResponseEntity.ok(mainService.findMatches(request));
    }

    @ApiOperation("Редактирование записи черного списка")
    @PutMapping("/{black-list-type}/{record-id}")
    public ResponseEntity updateRecord(@PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId,
                                       @RequestBody Map<String, String> values){
        mainService.updateRecord(blType,recordId, values);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Удаление записи из черного списка")
    @DeleteMapping("/{black-list-type}/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId) {
        mainService.deleteRecord(blType, 0, recordId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Получение всех записей по типу черного списка")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BlackListRecordDTO>> getRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        return ResponseEntity.ok().body(mainService.getRecords(PageRequest.of(page, size)));
    }
}
