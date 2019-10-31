package com.darakay.micro689.sources;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.dto.BlackListRecordDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GetRecordsTest extends AbstractTest{

    private static final String URL = "/api/v1/black-list";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnListOfAllRecords_WithoutPagination() throws Exception {

        MvcResult result = mockMvc.perform(authenticateGetRequest(URL, "test_user", "test_pw"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotEqualTo("[]");
    }

    @Test
    public void shouldReturnListOfRecords_WithPagination() throws Exception {

        MvcResult result = mockMvc.perform(
                authenticateGetRequest(URL, "test_user", "test_pw")
                .param("page", "0")
                .param("size", "2"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<BlackListRecordDTO> actual = new ObjectMapper()
                .readValue(contentAsString, new TypeReference<List<BlackListRecordDTO>>(){});
        assertThat(actual).asList().hasSize(2);
    }

    @Test
    public void shouldReturnListOfRecordsBelongsCurrentUser_WithoutPagination() throws Exception {

        MvcResult result = mockMvc.perform(
                authenticateGetRequest(URL, "test_user2", "test_pw2"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(
                "[{\"id\":10006,\"personalInfo\":{},\"passportInfo\":{},\"email\":\"+78953475143\"}]"
        );
    }

}