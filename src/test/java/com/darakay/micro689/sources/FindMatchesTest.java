package com.darakay.micro689.sources;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindMatchesRequest;
import com.darakay.micro689.dto.PassportInfoDTO;
import com.darakay.micro689.dto.PersonalInfoDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonInclude;
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
@EnableAspectJAutoProxy
public class FindMatchesTest extends AbstractTest {

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
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .partnerId(0)
                .build();

        mockMvc.perform(
                    authenticatePostRequest(URL, "test_user", "test_pw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_SearchesAllPartiallyRecordsWithoutPartnerId_WhenMatchesExist() throws Exception {
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo((new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01"))))
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .build();

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithoutPartnerId() throws Exception {
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .passportInfo(new PassportInfoDTO("6538", "275396"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .build();

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnSeveralBlocks_WithoutPartnerId() throws Exception {
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-02-01")))
                .passportInfo(new PassportInfoDTO("6538", "275396"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .build();

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereAreNotMatchesOnAllBlocks_WithoutPatrnerId() throws Exception {
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Петров", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-02-01")))
                .passportInfo(new PassportInfoDTO("1274", "783486"))
                .inn("894623")
                .phone("+78959075143")
                .email("ivan@narod.ru")
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .build();

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnCorrectResult_WhenThereIsMatchOnAllBlocks_WithPartnerId() throws Exception {
        BlackListRecordDTO example = BlackListRecordDTO.builder()
                .personalInfo(new PersonalInfoDTO("Иванов", "Иван",
                        "Иванович", java.sql.Date.valueOf("1970-01-01")))
                .passportInfo(new PassportInfoDTO("6754", "985634"))
                .inn("657895")
                .phone("+78953475143")
                .email("ivan@yandex.ru")
                .build();

        FindMatchesRequest request = FindMatchesRequest.builder()
                .example(example)
                .partnerId(1234)
                .build();

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("2"))
                .andExpect(jsonPath("$.responseDate").value(currentDate));
    }

    @Test
    public void findRecord_ReturnNegativeResult_WhenNoRequiredFields() throws Exception {
        Map<String, Map<String, String>> example = new HashMap<>();
        Map<String, String> passportInfo = new HashMap<>();
        passportInfo.put("passportSeria", "1234");
        example.put("passportInfo", passportInfo);

        Map<String, Map<String, Map<String, String>>> request = new HashMap<>();
        request.put("example", example);

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("0"))
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле в 'passportInfo'"));
    }

    @Test
    public void findRecord_IgnoreIdField() throws Exception {
        Map<String, String> testRequest = new HashMap<>();
        testRequest.put("email", "asdf@tyui");
        testRequest.put("id", "12345678");

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("0"))
                .andExpect(jsonPath("$.message").value("Некорректный формат запроса"));
    }

    @Test
    public void findRecord_IgnoreUnknownFields() throws Exception {
        Map<String, String> example = new HashMap<>();
        example.put("email", "asdf@tyui");
        example.put("value", "12345678");

        Map<String, Map<String, String>> testRequest = new HashMap<>();
        testRequest.put("example", example);

        mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("0"))
                .andExpect(jsonPath("$.message").value("Некорректный формат запроса"));
    }

}