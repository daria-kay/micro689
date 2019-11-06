package com.darakay.micro689.repo;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.ExampleDTO;
import com.darakay.micro689.dto.FindRecordsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyBatisRecordRepository {
    boolean findMatchesWithPartnerId(@Param("partnerId") Integer partnerId, @Param("example") ExampleDTO request);
    boolean findMatches(ExampleDTO request);

    int insertMapAsRecord(Map<String, Integer> recordValues);

    List<Integer> findRecords(@Param("userId") int userId, @Param("request") FindRecordsRequest request);
}
