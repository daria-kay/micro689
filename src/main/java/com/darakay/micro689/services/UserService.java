package com.darakay.micro689.services;

import com.darakay.micro689.domain.Partner;
import com.darakay.micro689.domain.User;
import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.repo.PartnerRepository;
import com.darakay.micro689.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    public UserService(UserRepository userRepository, PartnerRepository partnerRepository) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
    }

    public int logUp(LogupRequest request) {
        Partner partner = partnerRepository.findById(request.getPartnerId());
        User user = User.builder()
                .login(request.getLogin())
                .password(request.getPasswordHash())
                .partner(partner)
                .build();
        userRepository.save(user);
        return user.getId();
    }
}
