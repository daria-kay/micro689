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

    private final static CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withDelimiter(';');

    public List<Map<String, String>> read(MultipartFile file, String[] headers){
        return parseCSVFile(file, headers).stream().map(CSVRecord::toMap).collect(Collectors.toList());
    }

    private List<CSVRecord> parseCSVFile(MultipartFile file, String[] headers){
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(file.getBytes()))) {
            return CSV_FORMAT.withHeader(headers)
                    .parse(reader).getRecords();
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException();
        }
    }
 }
