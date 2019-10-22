package com.darakay.micro689.exception;

public class InvalidRecordFormatException extends RuntimeException {
    public InvalidRecordFormatException(String message) {
        super(message);
    }

    public static InvalidRecordFormatException wrongDateFormat() {
        return new InvalidRecordFormatException("Неверный формат даты рождения." +
                " Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)");
    }

    public static InvalidRecordFormatException missingRequiredField(String fieldName) {
        return new InvalidRecordFormatException("Не заполнено обязательное поле '"+fieldName+"'");
    }

    public static InvalidRecordFormatException emptyValuesMap() {
        return new InvalidRecordFormatException("Отсутствуют поля для обновления");
    }

    public static InvalidRecordFormatException uknownField(String fieldName) {
        return new InvalidRecordFormatException("Некорректное поле '"+fieldName+"'");
    }
}
