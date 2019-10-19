package com.darakay.micro689.exception;

public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(String message) {
        super(message);
    }

    public static InvalidFileFormatException wrongFieldCount(int expectedCount, int actualCount) {
        return new InvalidFileFormatException(
                String.format("Неверное количество столбцов с csv файле. Ожидалось %s, получено %s",
                        expectedCount, actualCount));
    }

    public static InvalidFileFormatException wrongDateFormat() {
        return new InvalidFileFormatException("Неверный формат даты рождения." +
                " Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)");
    }
}
