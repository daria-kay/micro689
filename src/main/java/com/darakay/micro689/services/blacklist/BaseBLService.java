package com.darakay.micro689.services.blacklist;

import com.darakay.micro689.exception.CannotReadFileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class BaseBLService {

    public void parseAndSaveCSVFile(MultipartFile multipartFile, int creatorId) {
        try(Reader reader = new InputStreamReader(new ByteArrayInputStream(multipartFile.getBytes()));) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').parse(reader);
            storeRecords(records, creatorId);
            parser.close();
        } catch (IllegalStateException | IOException e) {
            throw new CannotReadFileException();
        }
    }

    abstract void storeRecords(Iterable<CSVRecord> records, int creatorId);
}