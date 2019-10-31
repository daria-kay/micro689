package com.darakay.micro689.sources;

import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableAspectJAutoProxy
public class LogupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldLogUpNewUser_WhenRequestIsCorrect() throws Exception {
        LogupRequest request = LogupRequest.builder().login("user1").passwordHash("asdf").partnerId(0).build();

        mockMvc.perform(post("/api/v1/logup")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        assertThat(userRepository.existsById(1)).isTrue();
    }

    @Test
    public void shouldReturn400_WhenRequestIsInvalid_NonUniqueUserName() throws Exception {
        LogupRequest request = LogupRequest.builder().login("test_user").passwordHash("asdf").partnerId(0).build();

        mockMvc.perform(post("/api/v1/logup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Пользователь с логином 'test_user' уже существует"));
    }

    @Test
    public void shouldReturn400_WhenRequestIsInvalid_NonexistentPartnerId() throws Exception {
        LogupRequest request = LogupRequest.builder().login("user2").passwordHash("asdf").partnerId(12345).build();

        mockMvc.perform(post("/api/v1/logup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Несуществующий partner_id '12345'"));
    }

    @Test
    public void shouldReturn400_WhenRequestIsInvalid_InvalidUserName() throws Exception {
        LogupRequest request = LogupRequest.builder().login("a b").passwordHash("asdf").partnerId(0).build();

        mockMvc.perform(post("/api/v1/logup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Логин может содержать только латинские буквы (заглавные и прописные) и" +
                                " цифры. Максимальный размер логина 100 символов"));
    }

    @Test
    public void shouldReturn400_WhenRequestIsInvalid_UserNameIsNull() throws Exception {
        LogupRequest request = LogupRequest.builder().login(null).passwordHash("asdf").partnerId(0).build();

        mockMvc.perform(post("/api/v1/logup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле"));
    }
}