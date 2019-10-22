package com.darakay.micro689.sources;

import com.darakay.micro689.repo.InnBLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteBlackListRecordTest {
    private final static String URL = "/api/v1/black-list/{black-list-type}/{record-id}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InnBLRepository innBLRepository;

    @Test
    public void deleteRecord_RecordIdIsValid() throws Exception {
        mockMvc.perform(delete(URL, "inn", "1234")).andExpect(status().isNoContent());

        assertThat(innBLRepository.existsById(1234)).isFalse();
    }

    @Test
    public void return404ResponseCode_NonexistentRecord() throws Exception {
        mockMvc.perform(delete(URL, "inn", "1235")).andExpect(status().isNotFound());

        assertThat(innBLRepository.existsById(1235)).isFalse();
    }
}