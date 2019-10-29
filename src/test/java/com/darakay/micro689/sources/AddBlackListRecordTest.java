package com.darakay.micro689.sources;

import com.darakay.micro689.repo.RecordsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddBlackListRecordTest extends AbstractTest{

    private static final String URL = "/api/v1/black-list/add-entry-task";

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveBlackListRecord_WhenRequestIsCorrect() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Иванов");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("/api/v1/black-list/1"));

        assertThat(recordsRepository.existsById(1)).isTrue();
    }

    @Test
    public void return400Response_WhenNoRequiredField() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Петров");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле 'firstName'"));
    }

    @Test
    public void return400Response_WhenRequestValueIsInvalid_InvalidFormat() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Петров");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987/07/14");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Неверный формат даты рождения. " +
                                "Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)"));
    }

    @Test
    public void return400Response_WhenRequestIsInvalid_TooLong() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("passportSeria", "123456");
        map.put("passportNumber", "123456");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Серия пасспорта может содержать только 4 знака"));
    }

    @Test
    public void ignore_WhenFieldValueIsInvalid_UnknownField() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("passportSeria", "1234");
        map.put("passportNumber", "asdyuo");
        map.put("name", "value");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());
    }

    @Test
    public void return415Response_WhenNoContentTypeHeader() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Петров");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void return403Response_WhenNoAuthenticationHeader() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("surname", "Иванов");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void return401Response_WhenPasswordIsInvalid() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("surname", "Иванов");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "invalid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void return401Response_WhenLoginIsInvalid() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("surname", "Иванов");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(
                authenticatePostRequest(URL, "invalid", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isUnauthorized());
    }
}