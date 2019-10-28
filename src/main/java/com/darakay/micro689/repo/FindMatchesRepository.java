package com.darakay.micro689.repo;

import com.darakay.micro689.dto.MatchSearchRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindMatchesRepository {
    boolean findMatchesWithPartnerId(MatchSearchRequest request);
    boolean findMatches(MatchSearchRequest request);
}
