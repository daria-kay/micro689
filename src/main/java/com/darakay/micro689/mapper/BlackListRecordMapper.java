package com.darakay.micro689.mapper;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.PassportInfoDTO;
import com.darakay.micro689.dto.PersonalInfoDTO;
import com.darakay.micro689.exception.InternalServerException;
import com.darakay.micro689.exception.InvalidRecordFormatException;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlackListRecordMapper<BlRecordType> {


    private final Supplier<BlRecordType> newBlackListRecord;
    private final Set<String> requiredFields;
    private final Set<String> nullableFields;

    private BlackListRecordMapper(Supplier<BlRecordType> newBlackListRecord, Set<String> fieldNames, Set<String> nullableFields) {
        this.newBlackListRecord = newBlackListRecord;
        this.requiredFields = fieldNames;
        this.nullableFields = nullableFields;
    }

    public static BlackListRecordDTO mapToDTO(Record record){
        return BlackListRecordDTO.builder()
                .id(record.getId())
                .personalInfo(new PersonalInfoDTO(record.getPersonalInfo()))
                .passportInfo(new PassportInfoDTO(record.getPassportInfo()))
                .inn(record.getInn().getInn())
                .phone(record.getPhone().getPhone())
                .email(record.getEmail().getEmail())
                .build();
    }

    public static <BlackListRecordType> BlackListRecordMapper<BlackListRecordType> forRecord(
            Supplier<BlackListRecordType> blackListRecordInitialzr){
        return new BlackListRecordMapper<>(blackListRecordInitialzr,
                Stream.of(blackListRecordInitialzr.get().getClass().getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new)), ImmutableSet.of());
    }

    public BlRecordType mapToBlackListRecord(Map<String, String> fields){
        BlRecordType record = newBlackListRecord.get();
        Stream.of(record.getClass().getDeclaredFields())
                .forEach(field -> checkAndSetValue(fields, field, record,
                        InvalidRecordFormatException.missingRequiredField(field.getName())));
        return record;
    }

    public BlRecordType updateRecordFields(Map<String, String> values, BlRecordType record) {
        if (values.isEmpty())
            throw InvalidRecordFormatException.emptyValuesMap();
        values.keySet().forEach(key -> {
            if (!requiredFields.contains(key))
                throw InvalidRecordFormatException.uknownField(key);
        });
        Stream.of(record.getClass().getDeclaredFields())
                .filter(field -> values.containsKey(field.getName()))
                .forEach(field -> setValue(values.get(field.getName()), field, record));
        return record;
    }

    private void checkAndSetValue(Map<String, String> values, Field field,
                                  BlRecordType blRecordType, RuntimeException ex){
        String fieldName = field.getName();
        String value = values.get(fieldName);
        if(requiredFields.contains(fieldName) && value == null) {
            throw ex;
        }
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
                field.set(record, value);
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
        Set<String> diff = Sets.difference(requiredFields, new HashSet<>(Arrays.asList(excludedFieldsNames)));
        return new BlackListRecordMapper<>(newBlackListRecord, diff, nullableFields);
    }

    public BlackListRecordMapper<BlRecordType> nullableFields(String... nullableFieldsNames) {
        Set<String> setOf = new HashSet<>(Arrays.asList(nullableFieldsNames));
        Set<String> diff = Sets.difference(requiredFields, setOf);
        return new BlackListRecordMapper<>(newBlackListRecord, diff, setOf);
    }
}
