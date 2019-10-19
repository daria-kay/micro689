package com.darakay.micro689.domain;


import com.darakay.micro689.exception.InvalidFileFormatException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "full_filled_bl")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullFilledBLRecord extends BlackListRecord{
    private final static int CSV_COLUMN_COUNT = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;
    @Column(name = "pass_ser")
    private String passportSeria;
    @Column(name = "pass_num")
    private String passportNumber;
    @Column(name = "inn_num")
    private String inn;
    private String phone;
    private String email;

    public FullFilledBLRecord(CSVRecord csvRecord, int creatorId){
        if(csvRecord.size() != CSV_COLUMN_COUNT)
            throw InvalidFileFormatException.wrongFieldCount(CSV_COLUMN_COUNT, csvRecord.size());
        this.creatorId = creatorId;
        this.surname = csvRecord.get(0);
        this.firstName = csvRecord.get(1);
        this.secondName = csvRecord.get(2);
        this.birthDate = convertToSqlDate(csvRecord.get(3));
        this.passportSeria = csvRecord.get(4);
        this.passportNumber = csvRecord.get(5);
        this.inn = csvRecord.get(6);
        this.phone = csvRecord.get(7);
        this.email = csvRecord.get(8);
    }

}
