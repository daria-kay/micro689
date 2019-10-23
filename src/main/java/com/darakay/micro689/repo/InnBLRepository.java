package com.darakay.micro689.repo;

import com.darakay.micro689.domain.InnBLRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InnBLRepository extends PagingAndSortingRepository<InnBLRecord, Integer> {
    List<InnBLRecord> findByInn(String innNumber);
}
