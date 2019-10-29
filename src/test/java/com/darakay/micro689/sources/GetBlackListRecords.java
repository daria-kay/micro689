package com.darakay.micro689.sources;

import org.assertj.core.util.Files;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetBlackListRecords extends AbstractTest {
    private final static String URL = "/api/v1/black-list/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnRecordsList_WithoutPagination() throws Exception {
       mockMvc.perform(authenticateGetRequest(URL, "test_user", "test_pw"))
               .andExpect(status().isOk())
               .andExpect(content()
                        .json(Files.contentOf(new File("src/test/resources/exp_records.json"),
                                StandardCharsets.UTF_8)));
    }

    @Test
    public void returnRecordsList_OnlyUserRecords() throws Exception {
        mockMvc.perform(authenticateGetRequest(URL, "test_user2", "test_pw2"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(Files.contentOf(new File("src/test/resources/exp_records.json"),
                                StandardCharsets.UTF_8)));
    }



}