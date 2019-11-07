package com.darakay.micro689.resources;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.repo.PassportInfoBLRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateBlackListRecordTest extends AbstractTest{

    private final static String URL = "/api/v1/black-list/";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassportInfoBLRepository passportInfoBLRepository;

    @Test
    public void shouldUpdateRecord_RecordFieldsAreCorrect() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("passportNumber", "907645");

        mockMvc.perform(
                    authenticatePutRequest(URL+"passport-info/10001", "test_user", "test_pw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isOk());
        Record record = recordsRepository.findById(10001).get();
        assertThat(record.getPassportInfo().getPassportNumber()).isEqualTo("907645");
    }

    @Test
    public void shouldReturn403_RecordDoesNotBelongUser() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("phone", "+78965388637");

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/10005", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isForbidden());
        Record record = recordsRepository.findById(10005).get();
        assertThat(record.getPhone().getPhone()).isEqualTo("+78953475143");
    }

    @Test
    public void shouldReturn400ResponseCode_UnknownFieldsNames() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("surname", "Иванов");

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/10001", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Некорректное поле 'surname'"));

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn400ResponseCode_EmptyRequestDto() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/10001", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Отсутствуют поля для обновления"));

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn400ResponseCode_UnmappedFields() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("passportSeria", "7845");
        testMap.put("creatorId", "0");

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/10001", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Некорректное поле 'creatorId'"));

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn415ResponseCode_UnsupportedMediaType() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/10001", "test_user", "test_pw")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isUnsupportedMediaType());

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn404ResponseCode_NonexistentDigitsRecordId() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/678", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400ResponseCode_NonexistentStringRecordId() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                authenticatePutRequest(URL+"passport-info/vg45", "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn401ResponseCode() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                put(URL+"passport-info/10001")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isUnauthorized());
    }
}