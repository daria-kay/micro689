package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.exception.CannotReadFileException;
import com.darakay.micro689.exception.InternalServerException;
import com.darakay.micro689.exception.InvalidRecordFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class BaseBlackListService<BlRecordType> {
    private final static CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withDelimiter(';');

    public abstract void storeRecords(int creatorId, MultipartFile file);

    public abstract int storeRecord(int creatorId, Map<String, String> values);

    abstract String[] getFieldsNames();

    protected Iterable<BlRecordType> parseSCVFile(MultipartFile multipartFile, Supplier<BlRecordType> newEmptyRecord) {
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(multipartFile.getBytes()))) {
            return StreamSupport.stream(CSV_FORMAT.withHeader(getFieldsNames()).parse(reader).spliterator(), true)
                    .map(record -> mapToBlackListRecordType(record, newEmptyRecord.get()))
                    .collect(Collectors.toList());
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException();
        }
    }

    protected BlRecordType mapToBlackListRecordType(Map<String, String> request, BlRecordType emptyRecord){
        Set<String> setOfNames = Stream.of(getFieldsNames()).collect(Collectors.toSet());
        Stream.of(emptyRecord.getClass().getDeclaredFields())
                .filter(field -> setOfNames.contains(field.getName()))
                .forEach(field -> setValue(request.get(field.getName()), field, emptyRecord));
        return emptyRecord;
    }

    protected BlRecordType mapToBlackListRecordType(CSVRecord record, BlRecordType emptyRecord){
        return mapToBlackListRecordType(record.toMap(), emptyRecord);
    }

    private void setValue(String value, Field field, BlRecordType record){
        if(value == null)
            throw InvalidRecordFormatException.missingRequiredField(field.getName());
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