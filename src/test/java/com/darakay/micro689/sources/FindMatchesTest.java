package com.darakay.micro689.sources;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.PassportInfoDTO;
import com.darakay.micro689.dto.PersonalInfoDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonInclude;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FindMatchesTest {

    private final static String URL = "/api/v1/black-list/find-matches-task";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Before
    public void setUp(){

    }

    @Test
    public void findRecord_SearchesPartiallyRecordsWithPartnerId_WhenMatchesExist() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .partnerId(0).build();

        mockMvc.perform(
                    post(URL)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_SearchesAllPartiallyRecordsWithoutPartnerId_WhenMatchesExist() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .personalInfo((new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01"))))
                .build();

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithoutPartnerId() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .passportInfo(new PassportInfoDTO("6538", "275396"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnSeveralBlocks_WithoutPartnerId() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-02-01")))
                .passportInfo(new PassportInfoDTO("6538", "275396"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereAreNotMatchesOnAllBlocks_WithoutPatrnerId() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Петров", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-02-01")))
                .passportInfo(new PassportInfoDTO("1274", "783486"))
                .inn("894623")
                .phone("+78959075143")
                .email("ivan@narod.ru")
                .build();

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithPartnerId() throws Exception {
        BlackListRecordDTO request = BlackListRecordDTO.builder()
                .partnerId(1234)
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .passportInfo(new PassportInfoDTO("6754", "985634"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

//    @Test
//    public void findRecord_ReturnNegativeResult_WhenNoRequiredFields() throws Exception {
//        HashMap<String, Map<String, String>> request = new HashMap<>();
//        HashMap<String, String> passportInfo = new HashMap<>();
//        passportInfo.put("passportSeria", "1234");
//        request.put("passportInfo", passportInfo);
//
//
//        mockMvc.perform(
//                post(URL)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(mapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("0"))
//                .andExpect(jsonPath("$.message")
//                        .value("Не заполнено обязательное поле 'passportNumber'"));
//    }

//    @Test
//    public void findRecord_IgnoreIdField() throws Exception {
//        Map<String, String> testRequest = new HashMap<>();
//        testRequest.put("email", "asdf@tyui");
//        testRequest.put("id", "12345678");
//
//        mockMvc.perform(
//                post(URL)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(mapper.writeValueAsString(testRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("0"))
//                .andExpect(jsonPath("$.message").value("Неизвестное поле 'id'"));
//    }
//
//    @Test
//    public void findRecord_IgnoreUnknownFields() throws Exception {
//        Map<String, String> testRequest = new HashMap<>();
//        testRequest.put("email", "asdf@tyui");
//        testRequest.put("value", "12345678");
//
//        mockMvc.perform(
//                post(URL)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(mapper.writeValueAsString(testRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("0"))
//                .andExpect(jsonPath("$.message").value("Неизвестное поле 'value'"));
//    }

}