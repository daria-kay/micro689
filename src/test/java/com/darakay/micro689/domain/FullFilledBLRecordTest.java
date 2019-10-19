package com.darakay.micro689.domain;

import com.darakay.micro689.exception.InvalidFileFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.io.input.CharSequenceReader;

import java.io.IOException;
import java.sql.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class FullFilledBLRecordTest {

    @Test
    public void shouldCorrectMap_fromCSVRecord_HappyPath() throws IOException {
        CSVRecord testRecords =
                getTestRecord("Иванов;Иван;Иванович;1987-07-07;1234;123456;123456;+7895634782;asd@asd.ru\n");
        FullFilledBLRecord expected = FullFilledBLRecord.builder()
                .creatorId(0)
                .surname("Иванов")
                .firstName("Иван")
                .secondName("Иванович")
                .birthDate(Date.valueOf("1987-07-07"))
                .passportNumber("123456")
                .passportSeria("1234")
                .inn("123456")
                .phone("+7895634782")
                .email("asd@asd.ru")
                .build();
        assertThat(new FullFilledBLRecord(testRecords, 0)).isNotNull();
        assertThat(new FullFilledBLRecord(testRecords, 0)).isEqualToIgnoringGivenFields(expected,
                "id");
    }


    @Test
    public void shouldThrowException_fromCSVRecord_WhenIncorrectNumberOfFields() throws IOException {
        CSVRecord testRecords =
                getTestRecord("Иванов;Иван;Иванович;1987-07-07;1234;123456;+7895634782;asd@asd.ru\n");
        assertThatThrownBy(() -> new FullFilledBLRecord(testRecords, 0))
                .isInstanceOf(InvalidFileFormatException.class)
                .hasMessage("Wrong number of csv field! Expected 9, but number is 8");
    }

    @Test
    public void shouldThrowException_fromCSVRecord_WhenDateFormatIsInvalid() throws IOException {
        CSVRecord testRecords =
                getTestRecord("Иванов;Иван;Иванович;1987/07/07;1234;123456;123456;+7895634782;asd@asd.ru\n");
        assertThatThrownBy(() -> new FullFilledBLRecord(testRecords, 0))
                .isInstanceOf(InvalidFileFormatException.class)
                .hasMessage("Wrong date format! Expected yyyy-[m]m-[d]d");
    }



    private CSVRecord getTestRecord(String string) throws IOException {
        return CSVFormat.RFC4180.withDelimiter(';').parse(new CharSequenceReader(string)).getRecords().get(0);
    }
}