package com.darakay.micro689.annotation;

import com.darakay.micro689.validation.RecordIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecordIdValidator.class)
public @interface ValidRecordId {
    String message() default "Invalid or nonexistent record id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
