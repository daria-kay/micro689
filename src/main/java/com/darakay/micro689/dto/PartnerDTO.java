package com.darakay.micro689.dto;

import com.darakay.micro689.domain.Partner;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@JsonAutoDetect
@Getter
public class PartnerDTO {
    @ApiModelProperty("Имя партнера")
    private String name;
    @ApiModelProperty("ID партнера")
    private Integer id;

    public PartnerDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
    }
}
