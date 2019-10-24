package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindMatchesResultDTO {

    @JsonProperty("status")
    private int status;

    @JsonProperty("responseDate")
    private String responseDate;

    @JsonProperty("message")
    private String message;

    private FindMatchesResultDTO(int status) {
        this.status = status;
        this.responseDate = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
    }

    private FindMatchesResultDTO(String message) {
        this.status = 0;
        this.message = message;
    }

    public static FindMatchesResultDTO grasefull(boolean matches){
        return new FindMatchesResultDTO(getStatus(matches));
    }

    private static int getStatus(boolean matches){
        if(matches)
            return 1;
        return 2;
    }

    public static FindMatchesResultDTO error(String message) {
        return new FindMatchesResultDTO(message);
    }
}
