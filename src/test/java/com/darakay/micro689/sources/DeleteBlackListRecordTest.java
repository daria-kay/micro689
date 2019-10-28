package com.darakay.micro689.sources;

import com.darakay.micro689.repo.PassportInfoBLRepository;
import com.darakay.micro689.repo.PersonalInfoBLRepository;
import com.darakay.micro689.repo.RecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeleteBlackListRecordTest {
    private final static String URL = "/api/v1/black-list/{record-id}";

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
        mockMvc.perform(delete(URL, "10003")).andExpect(status().isNoContent());

        assertThat(recordsRepository.existsById(10003)).isFalse();
        assertThat(personalInfoBLRepository.existsById(10003)).isFalse();
        assertThat(passportInfoBLRepository.existsById(10003)).isFalse();
    }

    @Test
    public void return404ResponseCode_NonexistentRecord() throws Exception {
        mockMvc.perform(delete(URL, "1235")).andExpect(status().is4xxClientError());
    }

    @Test
    public void return400ResponseCode_InvalidRecordId() throws Exception {
        mockMvc.perform(delete(URL, "a123")).andExpect(status().is4xxClientError());
    }
}