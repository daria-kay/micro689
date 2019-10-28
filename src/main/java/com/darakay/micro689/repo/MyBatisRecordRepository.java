package com.darakay.micro689.repo;

import com.darakay.micro689.dto.BlackListRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MyBatisRecordRepository {
    boolean findMatchesWithPartnerId(BlackListRecordDTO request);
    boolean findMatches(BlackListRecordDTO request);

    int insertMapAsRecord(Map<String, Integer> recordValues);
}
