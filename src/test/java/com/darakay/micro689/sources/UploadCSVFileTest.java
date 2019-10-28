package com.darakay.micro689.sources;

import com.darakay.micro689.repo.*;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")public class UploadCSVFileTest {

    private static final String UPLOAD_URL = "/api/v1/black-list/upload-task";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonalInfoBLRepository personalInfoBLRepository;

    @Autowired
    private PassportInfoBLRepository passportInfoBLRepository;

    @Autowired
    private InnBLRepository innBLRepository;

    @Autowired
    private PhoneBlRepository phoneBLRepository;

    @Autowired
    private EmailBLRepository emailBLRepository;

    private FakeValuesService fakeValuesService =
            new FakeValuesService(Locale.forLanguageTag("ru-RU"), new RandomService());


    @Test
    public void shouldUploadCsvFile_ToFullFilledBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForFullFilledBL(10))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(personalInfoBLRepository.existsBySurname("Босяков")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToPersonalInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForPersonalInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(personalInfoBLRepository.existsBySurname("Петров")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToPassportInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForPassportInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(passportInfoBLRepository.existsByPassportNumber("764598")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToInnBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForInnBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(innBLRepository.findByInn("123456")).hasSize(5);
    }

    @Test
    public void shouldUploadCsvFile_ToPhoneBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForPhoneBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(phoneBLRepository.existsById(5)).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToEmailBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL)
                .file(getFakeCsvContentForEmailBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
        assertThat(emailBLRepository.findByEmail("test.mail@mail.com")).hasSize(5);
    }

    @Test
    public void shouldReturn_400Response_ForInvalidFileFormat_MissingRequiredColumn() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL)
                        .file(new MockMultipartFile("csv", ("surname;firstName;birthDate\n" +
                                "Суриков;Иван;1987/07/07").getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле 'secondName'"));
    }

    @Test
    public void shouldReturn_400Response_ForInvalidFileFormat_WrongDataFormat() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL)
                        .file(new MockMultipartFile("csv", ("surname;firstName;secondName;birthDate\n" +
                                "Суриков;Иван;Иванович;1987/07/07").getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Неверный формат даты рождения." +
                                " Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)"));

        assertThat(personalInfoBLRepository.existsBySurname("Суриков")).isFalse();

    }

    @Test
    public void shouldReturn_400Response_ForMissingRequestParam() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturn_400Response_ForInvalidContentType() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL)
                        .file(new MockMultipartFile("csv", "content".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturn_400Response_ForInvalidCSVFile() throws Exception {
        byte[] content = new byte[10_000_000];
        new Random().nextBytes(content);
        mockMvc.perform(
                multipart(UPLOAD_URL)
                        .file(new MockMultipartFile("csv", content))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());

    }

    private MockMultipartFile getFakeCsvContentForEmailBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("email\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append("test.mail@mail.com");
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPhoneBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("phone\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("+7123456789"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForInnBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("inn\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("123456"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPassportInfoBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("passportSeria;passportNumber\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("[0-9]{4};"));
            sb.append(fakeValuesService.regexify("764598"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForFullFilledBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("surname;firstName;secondName;birthDate;passportSeria;passportNumber;inn;phone;email\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append("Босяков;");
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{5};"));
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{7};"));
            sb.append(fakeValuesService.regexify("19[0-9]{2}-[1-9]-[1-9];"));
            sb.append(fakeValuesService.regexify("[0-9]{4};"));
            sb.append(fakeValuesService.regexify("[0-9]{6};"));
            sb.append(fakeValuesService.regexify("[0-9]{6};"));
            sb.append(fakeValuesService.regexify("+7[0-9]{10};"));
            sb.append(fakeValuesService.regexify("[A-Za-z]{6}\\@[a-z]{3}\\.com"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPersonalInfoBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("surname;firstName;secondName;birthDate\n");
        for (int i = 0; i < recordCount; i++) {
            sb.append("Петров;");
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{5};"));
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{7};"));
            sb.append(fakeValuesService.regexify("1970-01-01"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }
}