package com.darakay.micro689.sources;

import com.darakay.micro689.annotation.ValidRecordId;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.dto.FindRecordsRequest;
import com.darakay.micro689.services.BlackListRecordService;
import io.swagger.annotations.*;
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

@Api("Операции с записями в черном списке")
@RestController
@RequestMapping("/api/v1/black-list")
public class BlackListResource {

    private final static String allowableBLNames = "personal-info, passport-info, inn, phone, email";

    private final BlackListRecordService blackListRecordService;

    public BlackListResource(BlackListRecordService blackListRecordService) {
        this.blackListRecordService = blackListRecordService;
    }

    @ApiOperation(value = "Загрузка записей из файла csv. Обязательно наличие csv заголовков surname, firstName," +
            " secondName, birthDate, passportSeria, passportNumber, inn, phone, mail над соответсвующими столбцами")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Запись добавлена", response = void.class),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
    })
    @PostMapping("/upload-task")
    @CrossOrigin(value = "*", methods = {OPTIONS, POST}, allowedHeaders = {"Authorization"})
    public ResponseEntity uploadCsvFile(Authentication authentication,
                                        @ApiParam(value = "csv файл", required = true) @RequestParam("csv")
                                                MultipartFile multipartFile) {
        blackListRecordService.storeRecords(authentication, multipartFile);
        return ResponseEntity.created(URI.create("")).build();
    }

    @ApiOperation(value = "Загрузка записей по одной.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Запись добавлена",  response = void.class),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
    })
    @PostMapping("/add-entry-task")
    @CrossOrigin(value = "*", methods = {OPTIONS, POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity addEntry(Authentication authentication,
                                   @ApiParam(value = "Поля записи") @RequestBody Map<String, String> request){
        int recordId = blackListRecordService.storeRecord(authentication, request);
        return ResponseEntity.created(URI.create("/api/v1/black-list/" + recordId)).build();
    }

    @ApiOperation(value = "Поиск совпадений в базе данных по предоставленному образцу")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запрос успешно выполнен",  response = FindMatchesResult.class),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос")
    })
    @PostMapping(value = "/find-matches-task", produces = "application/json;charset=UTF-8")
    @CrossOrigin(value = "*", methods = {OPTIONS, POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<FindMatchesResult> findMatches(@ApiParam(value = "Объект запроса на поиск совпадений")
                                                             @Validated @RequestBody FindMatchesRequest request){
        return ResponseEntity.ok(blackListRecordService.findMatches(request));
    }

    @ApiOperation(value = "Поиск записей по предоставленному образцу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Записи найдены"),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
    })
    @PostMapping(value = "/find-records-task", produces = "application/json;charset=UTF-8")
    @CrossOrigin(value = "*", methods = {OPTIONS, POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<List<BlackListRecordDTO>> findRecords(@ApiParam(value = "Объект запроса на поиск записей")
            @Validated @RequestBody FindRecordsRequest request, Authentication authentication){
        return ResponseEntity.ok(blackListRecordService.findRecords(request, authentication));
    }

    @ApiOperation(value = "Обновление полей существующей записи")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запись успешно обновлена", response = void.class),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос"),
            @ApiResponse(code = 403, message = "Запись принадлежит другому пользователю"),
            @ApiResponse(code = 404, message = "Запись не найдена"),
    })
    @PutMapping("/{black-list-type}/{record-id}")
    @CrossOrigin(value = "*", methods = {OPTIONS, PUT}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity updateRecord(@ApiParam(value = "Тип черного списка", allowableValues = allowableBLNames, required = true)
                                       @PathVariable("black-list-type") String blType,
                                       @PathVariable("record-id") int recordId,
                                       @ApiParam(value = "Обновленные поля") @RequestBody Map<String, String> values){
        blackListRecordService.updateRecord(blType,recordId, values);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Удаление записи")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Запись успешно удалена",  response = void.class),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос"),
            @ApiResponse(code = 403, message = "Запись принадлежит другому пользователю"),
            @ApiResponse(code = 404, message = "Запись не найдена"),
    })
    @DeleteMapping("/{record-id}")
    @CrossOrigin(value = "*", methods = {OPTIONS, DELETE}, allowedHeaders = {"Authorization"})
    public ResponseEntity deleteRecord(@ValidRecordId @PathVariable("record-id") Integer recordId) {
        blackListRecordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Получение всех записей с пагинацией")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Успешное получение записей"),
            @ApiResponse(code = 401, message = "Не аутентифицированный запрос")
    })
    @GetMapping(produces = "application/json")
    @CrossOrigin(value = "*", methods = {OPTIONS, GET}, allowedHeaders = {"Authorization"})
    public ResponseEntity<List<BlackListRecordDTO>> getRecords(
            Authentication authentication,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        return ResponseEntity.ok().body(blackListRecordService.getRecords(authentication, PageRequest.of(page, size)));
    }
}
