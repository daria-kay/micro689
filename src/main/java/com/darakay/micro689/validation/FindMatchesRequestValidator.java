package com.darakay.micro689.validation;

import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.PassportInfoDTO;
import com.darakay.micro689.dto.PersonalInfoDTO;
import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import org.springframework.stereotype.Component;

@Component
public class FindMatchesRequestValidator {

    public void validate(FindMatchesRequest request) {
        if(request.getExample() == null)
            throw InvalidFindMatchesRequestFormatException.invalidFormat();
        if(request.getExample().getPassportInfo() != null)
            checkPassportInfo(request.getExample().getPassportInfo());
        if(request.getExample().getPersonalInfo() != null)
            checkPersonalInfo(request.getExample().getPersonalInfo());
    }

    private void checkPassportInfo(PassportInfoDTO dto){
        if(dto.getPassportNumber() == null || dto.getPassportSeria() == null)
            throw InvalidFindMatchesRequestFormatException.missingRequiredField("passportInfo");
    }

    private void checkPersonalInfo(PersonalInfoDTO dto){
        if(dto.getSurname() == null || dto.getFirstName() == null ||
                dto.getSecondName() == null || dto.getBirthDate() == null)
            throw InvalidFindMatchesRequestFormatException.missingRequiredField("passportInfo");
    }
}
