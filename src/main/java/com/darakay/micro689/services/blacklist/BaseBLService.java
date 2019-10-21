package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.exception.CannotReadFileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class BaseBLService {
    private final static CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withDelimiter(';');

    public void storeCSVFile(MultipartFile multipartFile, int creatorId) {
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(multipartFile.getBytes()))) {
            storeRecords(readFileRecords(reader), creatorId);
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException();
        }
    }

    private Iterable<CSVRecord> readFileRecords(Reader fileReader) {
        try {
            return CSV_FORMAT.parse(fileReader);
        } catch (IOException e) {
            throw new UnsupportedOperationException();
        }
    }

    abstract void storeRecords(Iterable<CSVRecord> records, int creatorId);
}