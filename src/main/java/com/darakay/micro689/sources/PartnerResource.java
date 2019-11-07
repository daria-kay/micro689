package com.darakay.micro689.sources;

import com.darakay.micro689.dto.PartnerDTO;
import com.darakay.micro689.services.PartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
@RequestMapping("/api/v1/partner")
@Api(value = "Ресурс партнеров")
public class PartnerResource {

    private final PartnerService partnerService;

    public PartnerResource(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @ApiOperation(value = "Получение всех зарегестрированных партнеров")
    @GetMapping
    @CrossOrigin(value = "*", methods = {OPTIONS, GET}, allowedHeaders = {"Authorization"})
    public ResponseEntity<List<PartnerDTO>> getAllPartners(){
        return ResponseEntity.ok(partnerService.getAll());
    }
}
