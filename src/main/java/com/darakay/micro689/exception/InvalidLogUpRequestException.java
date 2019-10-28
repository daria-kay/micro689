package com.darakay.micro689.exception;

public class InvalidLogUpRequestException extends RuntimeException{
    public InvalidLogUpRequestException(String message) {
        super(message);
    }

    public static InvalidLogUpRequestException nonUniqueUserName(String login) {
        return new InvalidLogUpRequestException("Пользователь с логином '" + login + "' уже существует");
    }

    public static InvalidLogUpRequestException partnerIdNotExists(int partnerId) {
        return new InvalidLogUpRequestException("Несуществующий partner_id '"+ partnerId + "'");
    }

    public static InvalidLogUpRequestException invalidUserName() {
        return new InvalidLogUpRequestException("Логин может содержать только латинские буквы (заглавные и прописные)" +
                " и цифры. Максимальный размер логина 100 символов");
    }

    public static InvalidLogUpRequestException emptyRequiredField() {
        return new InvalidLogUpRequestException("Не заполнено обязательное поле");
    }
}
