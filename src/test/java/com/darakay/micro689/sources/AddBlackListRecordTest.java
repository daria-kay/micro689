package com.darakay.micro689.sources;

import com.darakay.micro689.repo.PersonalInfoBLRepository;
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
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddBlackListRecordTest {

    private static final String URL = "/api/v1/black-list/{list-type}/add-entry-task";

    @Autowired
    private PersonalInfoBLRepository personalInfoBLRepository;

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

        mockMvc.perform(post(URL, "personal-info")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());

        assertThat(personalInfoBLRepository.existsBySurname("Иванов")).isTrue();
    }

    @Test
    public void return400Response_WhenNoRequiredField() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Петров");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987-07-14");

        mockMvc.perform(post(URL, "personal-info")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле 'firstName'"));

        assertThat(personalInfoBLRepository.existsBySurname("Петров")).isFalse();
    }

    @Test
    public void return400Response_WhenRequestValueIsInvalid_InvalidFormat() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("surname", "Петров");
        map.put("firstName", "Иван");
        map.put("secondName", "Иванович");
        map.put("birthDate", "1987/07/14");

        mockMvc.perform(post(URL, "personal-info")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Неверный формат даты рождения. " +
                                "Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)"));

        assertThat(personalInfoBLRepository.existsBySurname("Петров")).isFalse();
    }

    @Test
    public void return400Response_WhenRequestIsInvalid_TooLong() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("passportSeria", "123456");
        map.put("passportNumber", "123456");

        mockMvc.perform(post(URL, "passport-info")
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

        mockMvc.perform(post(URL, "passport-info")
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

        mockMvc.perform(post(URL, "full-filled")
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isUnsupportedMediaType());

        assertThat(personalInfoBLRepository.existsBySurname("Петров")).isFalse();
    }
}