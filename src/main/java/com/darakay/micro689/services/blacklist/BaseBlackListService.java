package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.domain.BlackListRecord;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.exception.CannotReadFileException;
import com.darakay.micro689.exception.InternalServerException;
import com.darakay.micro689.exception.InvalidRecordFormatException;
import com.darakay.micro689.exception.RecordNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class BaseBlackListService<BlRecordType extends BlackListRecord,
        Repo extends PagingAndSortingRepository<BlRecordType, Integer>> {

    private Repo repository;
    private Function<Integer, BlRecordType> createRecordWith;

    private final static CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withDelimiter(';');

    protected BaseBlackListService(Repo repository, Function<Integer, BlRecordType> createRecordWith) {
        this.repository = repository;
        this.createRecordWith = createRecordWith;
    }

    public void storeRecords(int creatorId, MultipartFile file){
        repository.saveAll(parseSCVFile(file, createRecordWith, creatorId));
    }

    public int storeRecord(int creatorId, Map<String, String> values){
        BlRecordType record = mapToBlackListRecordType(values, createRecordWith.apply(creatorId));
        return repository.save(record).getId();
    }

    public void updateRecord(int recordId, Map<String, String> values){
        BlRecordType record = repository.findById(recordId)
                        .orElseThrow(RecordNotFoundException::new);
        BlRecordType updated = updateRecordFields(values, record);
        repository.save(updated);
    }

    @Transactional
    public void deleteRecord(int recordId){
        BlRecordType record = repository.findById(recordId).orElseThrow(RecordNotFoundException::new);
        repository.delete(record);
    }

    public List<BlackListRecordDTO> getRecords(Pageable pageable){
        return StreamSupport.stream(repository.findAll(pageable).spliterator(), true)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    abstract String[] getFieldsNames();

    private BlackListRecordDTO mapToDTO(BlRecordType record){
        BlackListRecordDTO dto = new BlackListRecordDTO();
        Set<String> setOfNames = Stream.of(getFieldsNames()).collect(Collectors.toSet());
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> setOfNames.contains(field.getName()))
                .forEach(field -> setValue(getFiledValue(field, record), getDTOField(field.getName()), dto));
        return dto;
    }

    private Field getDTOField(String name){
        try {
            return BlackListRecordDTO.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new InternalServerException();
        }
    }

    private String getFiledValue(Field field, Object obj){
        field.setAccessible(true);
        try {
            return field.get(obj).toString();
        } catch (IllegalAccessException e) {
            throw new InternalServerException();
        }
    }

    private Iterable<BlRecordType> parseSCVFile(MultipartFile multipartFile,
                                                  Function<Integer, BlRecordType> newEmptyRecord,
                                                  int creatorId) {
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(multipartFile.getBytes()))) {
            return StreamSupport.stream(CSV_FORMAT.withHeader(getFieldsNames()).parse(reader).spliterator(), true)
                    .map(record -> mapToBlackListRecordType(record, newEmptyRecord.apply(creatorId)))
                    .collect(Collectors.toList());
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException();
        }
    }

    private BlRecordType mapToBlackListRecordType(Map<String, String> request, BlRecordType emptyRecord){
        Set<String> setOfNames = Stream.of(getFieldsNames()).collect(Collectors.toSet());
        Stream.of(emptyRecord.getClass().getDeclaredFields())
                .filter(field -> setOfNames.contains(field.getName()))
                .forEach(field -> checkAndSetValue(request, field, emptyRecord));
        return emptyRecord;
    }

    private BlRecordType mapToBlackListRecordType(CSVRecord record, BlRecordType emptyRecord){
        return mapToBlackListRecordType(record.toMap(), emptyRecord);
    }

    private BlRecordType updateRecordFields(Map<String, String> values, BlRecordType record){
        if(values.isEmpty())
            throw InvalidRecordFormatException.emptyValuesMap();
        Set<String> names = Stream.of(getFieldsNames()).collect(Collectors.toSet());
        values.keySet().forEach(key -> {
            if(!names.contains(key))
                throw InvalidRecordFormatException.uknownField(key);
        });
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> values.containsKey(field.getName()))
                .forEach(field -> setValue(values.get(field.getName()), field, record));
        return record;
    }

    private void checkAndSetValue(Map<String, String> values, Field field, BlRecordType blRecordType){
        String value = Optional.ofNullable(values.get(field.getName()))
                .orElseThrow(() -> InvalidRecordFormatException.missingRequiredField(field.getName()));
        setValue(value, field, blRecordType);
    }

    private void setValue(String value, Field field, Object record){
        field.setAccessible(true);
        try {
            if(field.getType().equals(Date.class))
                field.set(record, convertToSqlDate(value));
            else
                field.set(record, value);
        } catch (IllegalAccessException e) {
            throw InternalServerException.cannotMap();
        }
    }

    private Date convertToSqlDate(String date) {
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            throw InvalidRecordFormatException.wrongDateFormat();
        }
    }
}