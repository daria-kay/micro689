package com.darakay.micro689.repo;

import com.darakay.micro689.dto.BlackListRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface MyBatisRecordRepository {
    boolean findMatchesWithPartnerId(@Param("partnerId") Integer partnerId, @Param("example")BlackListRecordDTO request);
    boolean findMatches(BlackListRecordDTO request);

    int insertMapAsRecord(Map<String, Integer> recordValues);
}
