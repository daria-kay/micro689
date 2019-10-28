package com.darakay.micro689.services;

import com.darakay.micro689.domain.BlackListRecord;
import com.darakay.micro689.exception.RecordNotFoundException;
import com.darakay.micro689.mapper.BlackListRecordMapper;
import com.google.common.collect.Sets;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class BlockRecordService<BlRecordType extends BlackListRecord,
        Repo extends CrudRepository<BlRecordType, Integer>> {

    private Repo repository;
    private final BlackListRecordMapper<BlRecordType> blackListRecordMapper;

    protected BlockRecordService(Repo repository, Supplier<BlRecordType> newRecord) {
        this.repository = repository;
        this.blackListRecordMapper =
                BlackListRecordMapper.forRecord(newRecord)
                        .excludeFields("id");
    }

    Integer storeRecord(Map<String, String> values){
        if(canHandle(values.keySet())){
            BlRecordType record = blackListRecordMapper.mapToBlackListRecord(values);
            return repository.save(record).getId();
        }
        return null;

    }
    void updateRecord(int recordId, Map<String, String> values){
        BlRecordType record = repository.findById(recordId)
                .orElseThrow(RecordNotFoundException::new);
        BlRecordType updated = blackListRecordMapper.updateRecordFields(values, record);
        repository.save(updated);
    }

    private boolean canHandle(Set<String> valueNames){
        Set<String> processedValues = new HashSet<>(Arrays.asList(getCSVHeaders()));
        return !Sets.intersection(processedValues, valueNames).isEmpty();
    }

    abstract String[] getCSVHeaders();
}