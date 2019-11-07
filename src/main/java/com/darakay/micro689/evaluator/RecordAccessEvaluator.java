package com.darakay.micro689.evaluator;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.exception.RecordNotFoundException;
import com.darakay.micro689.repo.RecordsRepository;
import org.springframework.stereotype.Component;

@Component
public class RecordAccessEvaluator {

    private final RecordsRepository recordsRepository;

    public RecordAccessEvaluator(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public boolean recordBelongsToUser(int userId, int recordId){
        Record record = recordsRepository.findById(recordId).orElseThrow(RecordNotFoundException::new);
        return record.getCreator().getId() == userId;
    }
}
