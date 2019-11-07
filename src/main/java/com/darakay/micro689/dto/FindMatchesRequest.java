package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchesRequest {
    @ApiModelProperty(value = "ID партнера, с которым ассоциирована запись")
    private Integer partnerId;
    @ApiModelProperty(value = "Образец записи для сравнения", required = true)
    private ExampleDTO example;
}
