package com.darakay.micro689.aspect;

import com.darakay.micro689.dto.LogupRequest;
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

    public ValidationAspect(LogUpRequestValidator logUpRequestValidator) {
        this.logUpRequestValidator = logUpRequestValidator;
    }

    @Pointcut("@annotation(com.darakay.micro689.annotation.ValidUserLogin)")
    public void validUserName(){
    }

    @Pointcut("execution(* com.darakay.micro689.sources.LoginController.logUp(com.darakay.micro689.dto.LogupRequest))")
    public void validatedLogUpRequest(){

    }

    @Before("validatedLogUpRequest()")
    public void validateUserName(JoinPoint joinPoint){
        LogupRequest request = (LogupRequest)joinPoint.getArgs()[0];
        logUpRequestValidator.validate(request);
    }

}
