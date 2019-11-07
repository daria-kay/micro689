package com.darakay.micro689.services;

import com.darakay.micro689.exception.CannotReadFileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CSVFileReader {

    private final static CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withFirstRecordAsHeader().withDelimiter(';');

    List<Map<String, String>> read(MultipartFile file){
        List<Map<String, String>> records = parseCSVFile(file).stream().map(CSVRecord::toMap).collect(Collectors.toList());
        if(records.isEmpty())
            throw CannotReadFileException.emptyRecordList();
        return records;
    }

    private List<CSVRecord> parseCSVFile(MultipartFile file){
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(file.getBytes()))) {
            return CSV_FORMAT.parse(reader).getRecords();
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException("Невозможно распарсить файл");
        }
    }
 }
