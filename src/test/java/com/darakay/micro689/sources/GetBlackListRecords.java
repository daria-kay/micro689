package com.darakay.micro689.sources;

import org.assertj.core.util.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetBlackListRecords {
    private final static String URL = "/api/v1/black-list/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnRecordsList_WithoutPagination() throws Exception {
       mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(Files.contentOf(new File("src/test/resources/exp_records.json"),
                                StandardCharsets.UTF_8)));
    }



}