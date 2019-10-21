package com.darakay.micro689.utils;

import com.darakay.micro689.exception.InvalidFileFormatException;

import java.sql.Date;

public class MapUtil {

    public static Date convertToSqlDate(String date) {
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            throw InvalidFileFormatException.wrongDateFormat();
        }
    }
}
