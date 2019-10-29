package com.darakay.micro689.evaluator;

import com.darakay.micro689.services.BlackListRecordService;
import org.springframework.stereotype.Component;

@Component
public class RecordAccessEvaluator {

    private final BlackListRecordService blackListRecordService;

    public RecordAccessEvaluator(BlackListRecordService blackListRecordService) {
        this.blackListRecordService = blackListRecordService;
    }

    public boolean recordBelongsToUser(int userId, int recordId){
        return blackListRecordService.recordsBelongToUser(userId, recordId);
    }
}
