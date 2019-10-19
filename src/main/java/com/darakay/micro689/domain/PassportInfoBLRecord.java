package com.darakay.micro689.domain;

import com.darakay.micro689.exception.InvalidFileFormatException;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;

@Entity
@Table(name = "passport_info_bl")
@NoArgsConstructor
public class PassportInfoBLRecord {
    private static final int CSV_COLUMN_COUNT = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    @Column(name = "pass_ser")
    private String passportSeria;
    @Column(name = "pass_num")
    private String passportNumber;

    public PassportInfoBLRecord(CSVRecord csvRecord, int creatorId) {
        if(csvRecord.size() != CSV_COLUMN_COUNT)
            throw InvalidFileFormatException.wrongFieldCount(CSV_COLUMN_COUNT, csvRecord.size());
        this.creatorId = creatorId;
        this.passportSeria = csvRecord.get(0);
        this.passportNumber = csvRecord.get(1);
    }
}
