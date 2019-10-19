package com.darakay.micro689.domain;

import com.darakay.micro689.exception.InvalidFileFormatException;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "personal_info_bl")
@NoArgsConstructor
public class PersonalInfoBLRecord extends BlackListRecord {
    private static final int CSV_COLUMN_COUNT = 4;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;

    public PersonalInfoBLRecord(CSVRecord csvRecord, int creatorId) {
        if(csvRecord.size() != CSV_COLUMN_COUNT)
            throw InvalidFileFormatException.wrongFieldCount(CSV_COLUMN_COUNT, csvRecord.size());
        this.creatorId = creatorId;
        this.surname = csvRecord.get(0);
        this.firstName = csvRecord.get(1);
        this.secondName = csvRecord.get(2);
        this.birthDate = convertToSqlDate(csvRecord.get(3));
    }
}
