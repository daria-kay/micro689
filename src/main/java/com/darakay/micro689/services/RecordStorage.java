package com.darakay.micro689.services;

import com.darakay.micro689.domain.InnBLRecord;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.mapper.BlackListRecordMapper;
import com.darakay.micro689.repo.FindMatchesRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("full-filled")
public class RecordStorage implements BLRecordStorage {

    private final String[] CSV_HEADERS = {"surname", "firstName", "secondName", "birthDate", "passportSeria",
            "passportNumber", "inn", "phone", "email"};

    private RecordsRepository recordsRepository;
    private FindMatchesRepository findMatchesRepository;
    private Set<PartialRecordStorage> services;
    private CSVFileReader csvFileReader;

    public RecordStorage(RecordsRepository recordsRepository, FindMatchesRepository findMatchesRepository,
                         Set<PartialRecordStorage> services, CSVFileReader csvFileReader) {
        this.recordsRepository = recordsRepository;
        this.findMatchesRepository = findMatchesRepository;
        this.services = services;
        this.csvFileReader = csvFileReader;
    }

    @Override
    public void storeRecords(int creatorId, MultipartFile file) {
       services.forEach(service -> {
           List<Map<String, String>> records = csvFileReader.read(file, CSV_HEADERS);
           service.storeRecords(creatorId, records);
       });
    }

    @Override
    public void storeRecord(int creatorId, Map<String, String> values) {;
        services.forEach(service -> service.storeRecord(creatorId, values));
    }

    public FindMatchesResult findMatches(BlackListRecordDTO request){
        if(request.getPartnerId() != null){
            boolean matches = findMatchesRepository.findMatchesWithPartnerId(request);
            return FindMatchesResult.gracefull(matches);
        }
        return FindMatchesResult.gracefull(findMatchesRepository.findMatches(request));
    }

    public List<BlackListRecordDTO> getRecords(Pageable pageable) {
        return StreamSupport.stream(recordsRepository.findAll(pageable).spliterator(), true)
                .map(BlackListRecordMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRecord(int recordId) {
        recordsRepository.deleteById(recordId);
    }
}
