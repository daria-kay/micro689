package com.darakay.micro689.services;

import com.darakay.micro689.dto.PartnerDTO;
import com.darakay.micro689.repo.PartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<PartnerDTO> getAll() {
        return StreamSupport.stream(partnerRepository.findAll().spliterator(), true)
                .map(PartnerDTO::new).collect(Collectors.toList());
    }
}
