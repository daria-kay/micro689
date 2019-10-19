package com.darakay.micro689.domain;

import com.darakay.micro689.exception.InvalidFileFormatException;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;

@Entity
@Table(name = "phone_bl")
@NoArgsConstructor
public class PhoneBLRecord {
    private final static int CSV_COLUMN_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    private String phone;

    public PhoneBLRecord(CSVRecord csvRecord, int creatorId){
        if(csvRecord.size() != CSV_COLUMN_COUNT)
            throw InvalidFileFormatException.wrongFieldCount(CSV_COLUMN_COUNT, csvRecord.size());
        this.creatorId = creatorId;
        this.phone = csvRecord.get(0);
    }
}
