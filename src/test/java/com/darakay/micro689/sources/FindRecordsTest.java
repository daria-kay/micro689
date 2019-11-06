package com.darakay.micro689.sources;

import com.darakay.micro689.dto.BlackListRecordDTO;
import com.darakay.micro689.dto.FindRecordsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FindRecordsTest extends AbstractTest{

    private static final String URL = "/api/v1/black-list/find-records-task";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturn200_WhenRequestIsCorrect() throws Exception {
        FindRecordsRequest request = FindRecordsRequest.builder()
                .surname("Иванов").build();

        MvcResult result = mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        List<BlackListRecordDTO> actual = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<BlackListRecordDTO>>(){});

        assertThat(actual).hasSize(3);
    }

    @Test
    public void shouldReturn200_WhenRequestIsCorrect_AndMoreSpecified() throws Exception {
        FindRecordsRequest request = FindRecordsRequest.builder()
                .surname("Иванов").email("searchTest@host.ru").build();

        MvcResult result = mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        List<BlackListRecordDTO> actual = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<BlackListRecordDTO>>(){});

        assertThat(actual).hasSize(1);
    }

    @Test
    public void shouldSearchOnlyCurrentUserRecords_WhenRequestIsCorrect() throws Exception {
        FindRecordsRequest request = FindRecordsRequest.builder()
                .phone("+78953475148").build();

        MvcResult result = mockMvc.perform(
                authenticatePostRequest(URL, "test_user", "test_pw")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        List<BlackListRecordDTO> actual = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<BlackListRecordDTO>>(){});

        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldReturn401_WhenNoAuthorizationHeader() throws Exception {
        FindRecordsRequest request = FindRecordsRequest.builder()
                .phone("+78953475148").build();

       mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

}