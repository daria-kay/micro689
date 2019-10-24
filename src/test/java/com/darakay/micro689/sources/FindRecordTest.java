package com.darakay.micro689.sources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FindRecordTest {

    private final static String URL = "/api/v1/black-list/find-record-task";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private String currentDate = new SimpleDateFormat("yyyy-mm-dd").format(new Date());

    @Test
    public void findRecord_SearchesPartiallyRecordsWithCreatorId_WhenMatchesExist() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("surname", "Иванов");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-01-01");
        testRequest.put("creatorId", "0");

        mockMvc.perform(
                    post(URL)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_SearchesAllPartiallyRecordsWithoutCreatorId_WhenMatchesExist() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("surname", "Иванов");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-01-01");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithoutCreatorId() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("surname", "Иванов");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-01-01");
        testRequest.put("passportSeria", "6538");
        testRequest.put("passportNumber", "275396");
        testRequest.put("inn", "657895");
        testRequest.put("phone", "+78953475143");
        testRequest.put("email", "ivan@yandex.ru");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnSeveralBlocks_WithoutCreatorId() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("surname", "Иванов");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-02-01");
        testRequest.put("passportSeria", "6538");
        testRequest.put("passportNumber", "275396");
        testRequest.put("inn", "657895");
        testRequest.put("phone", "+78959075143");
        testRequest.put("email", "ivan@yandex.ru");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereAreNotMatchesOnAllBlocks_WithoutCreatorId() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("surname", "Петров");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-02-01");
        testRequest.put("passportSeria", "1274");
        testRequest.put("passportNumber", "783486");
        testRequest.put("inn", "894623");
        testRequest.put("phone", "+78959075143");
        testRequest.put("email", "ivan@narod.ru");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithCreatorId() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("creatorId", "2457");
        testRequest.put("surname", "Иванов");
        testRequest.put("firstName", "Иван");
        testRequest.put("secondName", "Иванович");
        testRequest.put("birthDate", "1970-01-01");
        testRequest.put("passportSeria", "6538");
        testRequest.put("passportNumber", "275396");
        testRequest.put("inn", "657895");
        testRequest.put("phone", "+78953475143");
        testRequest.put("email", "ivan@yandex.ru");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnNegativeResult_WhenNoRequiredFields() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("passportSeria", "6538");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("0"))
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле 'passportNumber'"));
    }

    @Test
    public void findRecord_IgnoreIdField() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("passportSeria", "6538");
        testRequest.put("passportNumber", "275396");
        testRequest.put("id", "12345678");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_IgnoreUnknownFields() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("passportSeria", "6538");
        testRequest.put("passportNumber", "275396");
        testRequest.put("value", "12345678");

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

}