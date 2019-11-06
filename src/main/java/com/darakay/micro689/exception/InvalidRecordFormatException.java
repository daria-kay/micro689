package com.darakay.micro689.exception;

import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

public class InvalidRecordFormatException extends RuntimeException {

    private static Map fieldNames = ImmutableMap.builder()
            .put("surname", "Фамилия")
            .put("firstName", "Имя")
            .put("secondName", "Отчество")
            .put("birthDate", "Дата рождения")
            .put("passportSeria", "Серия паспорта")
            .put("passportNumber", "Номер паспорта")
            .put("inn", "ИНН")
            .put("phone", "Телофон")
            .put("email", "Почта")
            .build();


    public InvalidRecordFormatException(String message) {
        super(message);
    }

    public static InvalidRecordFormatException wrongDateFormat() {
        return new InvalidRecordFormatException("Неверный формат даты рождения." +
                " Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)");
    }

    public static InvalidRecordFormatException missingRequiredField(String fieldName) {
        Object name = Optional.ofNullable(fieldNames.get(fieldName)).orElse(fieldName);
        return new InvalidRecordFormatException("Не заполнено обязательное поле '"+ name +"'");
    }

    public static InvalidRecordFormatException emptyValuesMap() {
        return new InvalidRecordFormatException("Отсутствуют поля для обновления");
    }

    public static InvalidRecordFormatException uknownField(String fieldName) {
        return new InvalidRecordFormatException("Некорректное поле '"+fieldName+"'");
    }
}
