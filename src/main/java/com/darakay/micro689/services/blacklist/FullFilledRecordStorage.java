package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.services.CSVFileReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("full-filled")
public class FullFilledRecordStorage implements BLRecordStorage {

    private final String[] CSV_HEADERS = {"surname", "firstName", "secondName", "birthDate", "passportSeria",
            "passportNumber", "inn", "phone", "email"};

    private Set<PartialRecordStorage> services;
    private CSVFileReader csvFileReader;

    public FullFilledRecordStorage(Set<PartialRecordStorage> services, CSVFileReader csvFileReader) {
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
}
