package com.darakay.micro689.repo;

import com.darakay.micro689.dto.BlackListRecordDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindMatchesRepository {
    boolean findMatchesWithPartnerId(BlackListRecordDTO request);
    boolean findMatches(BlackListRecordDTO request);
}
