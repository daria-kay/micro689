package com.darakay.micro689.sources;

import com.darakay.micro689.repo.PassportInfoBLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
public class UpdateBlackListRecordTest {

    private final static String URL = "/api/v1/black-list/{black-list-type}/{record-id}";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassportInfoBLRepository passportInfoBLRepository;

    @Test
    public void shouldUpdateRecord_RecordFieldsAreCorrect() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("passportNumber", "907645");

        mockMvc.perform(
                    put(URL, "passport-info", "1234")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isOk());

        assertThat(passportInfoBLRepository.existsByPassportNumber("907645")).isTrue();
    }

    @Test
    public void shouldReturn400ResponseCode_UnknownFieldsNames() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("surname", "Иванов");

        mockMvc.perform(
                put(URL, "passport-info", "1234")
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
                put(URL, "passport-info", "1234")
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
                put(URL, "passport-info", "1234")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Некорректное поле 'creatorId'"));

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn400ResponseCode_EmptyRequestBody() throws Exception {
        mockMvc.perform(
                put(URL, "passport-info", "1234")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn415ResponseCode_UnsupportedMediaType() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                put(URL, "passport-info", "1234")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isUnsupportedMediaType());

        assertThat(passportInfoBLRepository.existsById(1234)).isTrue();
    }

    @Test
    public void shouldReturn404ResponseCode_NonexistentDigitsRecordId() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                put(URL, "passport-info", "678")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404ResponseCode_NonexistentStringRecordId() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();

        mockMvc.perform(
                put(URL, "passport-info", "ab678")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testMap)))
                .andExpect(status().isNotFound());
    }
}