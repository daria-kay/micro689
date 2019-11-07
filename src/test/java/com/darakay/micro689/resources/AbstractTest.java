package com.darakay.micro689.resources;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AbstractTest {
    protected MockHttpServletRequestBuilder authenticatePostRequest(String url, String login, String password) {
        return post(url)
                .header("Authorization", createAuthenticationToken(password, login));
    }

    protected MockHttpServletRequestBuilder authenticateDeleteRequest(String url, String login, String password) {
        return delete(url)
                .header("Authorization", createAuthenticationToken(password, login));
    }

    protected MockHttpServletRequestBuilder authenticateGetRequest(String url, String login, String password) {
        return get(url)
                .header("Authorization", createAuthenticationToken(password, login));
    }

    protected MockHttpServletRequestBuilder authenticatePutRequest(String url, String login, String password) {
        return put(url)
                .header("Authorization", createAuthenticationToken(password, login));
    }

    protected MockHttpServletRequestBuilder authenticateMultipartRequest(String url, String login, String password,
                                                                         MockMultipartFile file) {
        return multipart(url)
                .file(file)
                .header("Authorization", createAuthenticationToken(password, login));
    }

    private String createAuthenticationToken(String password, String username){
        return "Basic " + Base64.getEncoder().encodeToString((username+":"+password).getBytes());
    }
}
