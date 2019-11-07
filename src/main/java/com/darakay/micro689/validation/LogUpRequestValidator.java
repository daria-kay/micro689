package com.darakay.micro689.validation;

import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.exception.InvalidLogUpRequestException;
import com.darakay.micro689.repo.PartnerRepository;
import com.darakay.micro689.repo.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LogUpRequestValidator {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    public LogUpRequestValidator(UserRepository userRepository, PartnerRepository partnerRepository) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
    }

    public void validate(LogupRequest request) {
        String login = request.getLogin();
        if(login == null || request.getPasswordHash() == null)
            throw InvalidLogUpRequestException.emptyRequiredField();
        if(userRepository.existsByLogin(login))
            throw InvalidLogUpRequestException.nonUniqueUserName(login);
        if(!partnerRepository.existsById(request.getPartnerId()))
            throw InvalidLogUpRequestException.partnerIdNotExists(request.getPartnerId());
    }
}
