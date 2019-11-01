package com.darakay.micro689.aspect;

import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.validation.FindMatchesRequestValidator;
import com.darakay.micro689.validation.LogUpRequestValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    private final LogUpRequestValidator logUpRequestValidator;
    private final FindMatchesRequestValidator findMatchesRequestValidator;

    public ValidationAspect(LogUpRequestValidator logUpRequestValidator, FindMatchesRequestValidator findMatchesRequestValidator) {
        this.logUpRequestValidator = logUpRequestValidator;
        this.findMatchesRequestValidator = findMatchesRequestValidator;
    }

    @Pointcut("execution(* com.darakay.micro689.sources.AuthenticationSource.logUp(com.darakay.micro689.dto.LogupRequest))")
    public void validatedLogUpRequest(){

    }

    @Pointcut("execution(* com.darakay.micro689.sources.BlackListResource.findRecords(com.darakay.micro689.dto.FindMatchesRequest))")
    public void validatedFindMatchesRequest(){

    }

    @Before("validatedLogUpRequest()")
    public void validateUserName(JoinPoint joinPoint){
        LogupRequest request = (LogupRequest)joinPoint.getArgs()[0];
        logUpRequestValidator.validate(request);
    }

    @Before("validatedFindMatchesRequest()")
    public void validateFindMatchesRequest(JoinPoint joinPoint){
        FindMatchesRequest request = (FindMatchesRequest)joinPoint.getArgs()[0];
        findMatchesRequestValidator.validate(request);
    }

}
