package com.darakay.micro689.mapper;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.exception.InternalServerException;
import com.darakay.micro689.exception.InvalidRecordFormatException;
import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlackListRecordMapper<BlRecordType> {


    private final Supplier<BlRecordType> newBlackListRecord;
    private Set<String> fieldNames;

    private BlackListRecordMapper(Supplier<BlRecordType> newBlackListRecord) {
        this.newBlackListRecord = newBlackListRecord;
        fieldNames = Stream.of(newBlackListRecord.get().getClass().getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static <BlackListRecordType> BlackListRecordMapper<BlackListRecordType> forBlackListRecord(
            Supplier<BlackListRecordType> blackListRecordInitialzr){
        return new BlackListRecordMapper<BlackListRecordType>(blackListRecordInitialzr);
    }

    public BlRecordType mapToBlackListRecord(Map<String, String> fields){
        BlRecordType record = newBlackListRecord.get();
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> fieldNames.contains(field.getName()))
                .forEach(field -> checkAndSetValue(fields, field, record));
        return record;
    }

    public BlRecordType mapToBlackListRecordExample(Map<String, String> fields){
        BlRecordType record = newBlackListRecord.get();
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> fieldNames.contains(field.getName()))
                .forEach(field -> setUncheckedValues(fields, field, record));
        return record;
    }

    public BlackListRecordDTO mapToDTO(BlRecordType record){
        BlackListRecordDTO dto = new BlackListRecordDTO();
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> fieldNames.contains(field.getName()))
                .forEach(field -> setValue(getFiledValue(field, record), getDTOField(field.getName()), dto));
        return dto;
    }

    public BlRecordType updateRecordFields(Map<String, String> values, BlRecordType record){
        if(values.isEmpty())
            throw InvalidRecordFormatException.emptyValuesMap();
        values.keySet().forEach(key -> {
            if(!fieldNames.contains(key))
                throw InvalidRecordFormatException.uknownField(key);
        });
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> values.containsKey(field.getName()))
                .forEach(field -> setValue(values.get(field.getName()), field, record));
        return record;
    }

    private void setUncheckedValues(Map<String, String> values, Field field, BlRecordType blRecordType) {
        String fieldName = field.getName();
        if(fieldName.equals("creatorId")) {
            setValue(values.get(fieldName), field, blRecordType);
            return;
        }
        String value =  Optional.ofNullable(values.get(fieldName))
                .orElseThrow(() -> InvalidRequestFormatException.missingRequiredField(fieldName));
        setValue(value, field, blRecordType);
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

    private void checkAndSetValue(Map<String, String> values, Field field, BlRecordType blRecordType){
        String fieldName = field.getName();
        String value =  Optional.ofNullable(values.get(fieldName))
                .orElseThrow(() -> InvalidRecordFormatException.missingRequiredField(fieldName));
        setValue(value, field, blRecordType);
    }

    private void setValue(String value, Field field, Object record){
        field.setAccessible(true);
        try {
            if(value == null)
                return;
            if(field.getType().equals(Date.class)) {
                field.set(record, convertToSqlDate(value));
                return;
            }
            if (field.getType().equals(Integer.class)) {
                field.set(record, Integer.valueOf(value));
                return;
            }
            if (field.getType().equals(String.class)) {
                field.set(record, Integer.valueOf(value));
            }
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

    public BlackListRecordMapper<BlRecordType> excludeFields(String... excludedFieldsNames) {
        this.fieldNames = Sets.difference(fieldNames, ImmutableSet.of(excludedFieldsNames));
        return this;
    }
}
