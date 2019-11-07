package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindMatchesResult {

    @ApiModelProperty("Статус результата. 0 - произошла ошибка, 1 - записи найдены, 2 - записи не найдены")
    @JsonProperty("status")
    private int status;

    @ApiModelProperty("Дата выполнения запроса")
    @JsonProperty("responseDate")
    private String responseDate;

    @ApiModelProperty("Сообщение об ошибке")
    @JsonProperty("message")
    private String message;

    private FindMatchesResult(int status) {
        this.status = status;
        this.responseDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private FindMatchesResult(String message) {
        this.status = 0;
        this.message = message;
    }

    public static FindMatchesResult gracefull(boolean matches){
        return new FindMatchesResult(getStatus(matches));
    }

    private static int getStatus(boolean matches){
        if(matches)
            return 1;
        return 2;
    }

    public static FindMatchesResult error(String message) {
        return new FindMatchesResult(message);
    }
}
