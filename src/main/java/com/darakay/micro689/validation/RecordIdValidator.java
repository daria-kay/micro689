package com.darakay.micro689.validation;

import com.darakay.micro689.annotation.ValidRecordId;
import com.darakay.micro689.repo.RecordsRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RecordIdValidator implements ConstraintValidator<ValidRecordId, Integer> {

   private final RecordsRepository recordsRepository;

   public RecordIdValidator(RecordsRepository recordsRepository) {
      this.recordsRepository = recordsRepository;
   }

   public void initialize(ValidRecordId constraint) {
   }

   public boolean isValid(Integer id, ConstraintValidatorContext context) {
      return recordsRepository.existsById(id);
   }
}
