package com.darakay.micro689.services;

import com.darakay.micro689.domain.BlackListRecord;
import com.darakay.micro689.exception.RecordNotFoundException;
import com.darakay.micro689.mapper.BlackListRecordMapper;
import com.darakay.micro689.repo.BlackListRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class PartialRecordStorage<BlRecordType extends BlackListRecord,
        Repo extends BlackListRepository<BlRecordType>> implements BLRecordStorage {

    private Repo repository;
    private final BlackListRecordMapper<BlRecordType> blackListRecordMapper;
    private final CSVFileReader csvFileReader;

    protected PartialRecordStorage(Repo repository, Supplier<BlRecordType> newRecord, CSVFileReader csvFileReader) {
        this.repository = repository;
        this.blackListRecordMapper =
                BlackListRecordMapper.forRecord(newRecord)
                        .excludeFields("id")
                        .nullableFields("creatorId");
        this.csvFileReader = csvFileReader;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file){
        storeRecords(creatorId, csvFileReader.read(file, getCSVHeaders()));
    }

    @Override
    public void storeRecord(int creatorId, Map<String, String> values){
        assignRecordTo(creatorId, values);
        BlRecordType record = blackListRecordMapper.mapToBlackListRecord(values);
        repository.save(record);
    }

    void storeRecords(int creatorId, List<Map<String, String>> values){
        repository.saveAll(values.stream()
                .map(record -> assignRecordTo(creatorId, record))
                .map(blackListRecordMapper::mapToBlackListRecord)
                .collect(Collectors.toList()));
    }

    public void updateRecord(int recordId, Map<String, String> values){
        BlRecordType record = repository.findById(recordId)
                        .orElseThrow(RecordNotFoundException::new);
        BlRecordType updated = blackListRecordMapper.updateRecordFields(values, record);
        repository.save(updated);
    }


    private Map<String, String> assignRecordTo(int creatorId, Map<String, String> fields){
        fields.put("creatorId", String.valueOf(creatorId));
        return fields;
    }

    abstract String[] getCSVHeaders();
}