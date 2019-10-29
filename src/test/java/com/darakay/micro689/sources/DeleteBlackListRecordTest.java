package com.darakay.micro689.sources;

import com.darakay.micro689.repo.PassportInfoBLRepository;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeleteBlackListRecordTest extends AbstractTest{
    private final static String URL = "/api/v1/black-list/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private PersonalInfoBLRepository personalInfoBLRepository;

    @Autowired
    private PassportInfoBLRepository passportInfoBLRepository;

    @Test
    public void deleteRecord_RecordIdIsValid() throws Exception {
        mockMvc.perform(
                    authenticateDeleteRequest(URL+"10003", "test_user", "test_pw"))
                .andExpect(status().isNoContent());

        assertThat(recordsRepository.existsById(10003)).isFalse();
        assertThat(personalInfoBLRepository.existsById(10003)).isFalse();
        assertThat(passportInfoBLRepository.existsById(10003)).isFalse();
    }

    @Test
    public void return404ResponseCode_NonexistentRecord() throws Exception {
        mockMvc.perform(
                authenticateDeleteRequest(URL+"12345", "test_user", "test_pw"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void return400ResponseCode_InvalidRecordId() throws Exception {
        mockMvc.perform(
                authenticateDeleteRequest(URL+"a123", "test_user", "test_pw"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void return403ResponseCode_WhenRecordDoesNotBelongUser() throws Exception {
        mockMvc.perform(
                authenticateDeleteRequest(URL+"10004", "test_user", "test_pw"))
                .andExpect(status().is4xxClientError());
    }
}