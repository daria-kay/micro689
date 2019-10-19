package com.darakay.micro689.domain;


import com.darakay.micro689.exception.InvalidFileFormatException;

import java.sql.Date;

abstract class BlackListRecord {

    protected Date convertToSqlDate(String date){
        try{
            return Date.valueOf(date);
        } catch (IllegalArgumentException e){
            throw InvalidFileFormatException.wrongDateFormat();
        }
    }
}
