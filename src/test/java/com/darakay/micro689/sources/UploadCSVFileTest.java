package com.darakay.micro689.sources;

import com.darakay.micro689.repo.*;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
@Profile("test")
public class UploadCSVFileTest {

    private static final String UPLOAD_URL = "/api/v1/black-list/{black-list-type}/upload-task";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FullFilledBLRepository fullFilledBLRepository;

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
        mockMvc.perform(multipart(UPLOAD_URL, "full-filled")
                .file(getFakeCsvContentForFullFilledBL(10))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(fullFilledBLRepository.existsBySurname("Петров")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToPersonalInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "personal-info")
                .file(getFakeCsvContentForPersonalInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(personalInfoBLRepository.existsBySurname("Петров")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToPassportInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "passport-info")
                .file(getFakeCsvContentForPassportInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(passportInfoBLRepository.existsByPassportNumber("123456")).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToInnBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "inn")
                .file(getFakeCsvContentForInnBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(innBLRepository.findByInn("123456")).hasSize(5);
    }

    @Test
    public void shouldUploadCsvFile_ToPhoneBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "phone")
                .file(getFakeCsvContentForPhoneBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        assertThat(phoneBLRepository.existsById(5)).isTrue();
    }

    @Test
    public void shouldUploadCsvFile_ToEmailBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "email")
                .file(getFakeCsvContentForEmailBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
        assertThat(emailBLRepository.existsById(5)).isTrue();
    }

    @Test
    public void shouldReturn_404Response_forNonexistentBLType() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL, "some-black-list")
                        .file(new MockMultipartFile("csv", "filedata".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturn_400Response_ForInvalidFileFormat_MissingRequiredColumn() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL, "passport-info")
                        .file(new MockMultipartFile("csv", "1234".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Не заполнено обязательное поле 'passportNumber'"));
    }

    @Test
    public void shouldReturn_400Response_ForInvalidFileFormat_WrongDataFormat() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL, "personal-info")
                        .file(new MockMultipartFile("csv", "Иванов;Иван;Иванович;1987/07/07".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Неверный формат даты рождения." +
                                " Ожидается гггг-[м]м-[д]д (ведущий ноль опционален)"));

        assertThat(personalInfoBLRepository.existsBySurname("Иванов")).isFalse();

    }

    @Test
    public void shouldReturn_400Response_ForMissingRequestParam() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL, "personal-info")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturn_400Response_ForInvalidContentType() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_URL, "personal-info")
                        .file(new MockMultipartFile("csv", "content".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturn_400Response_ForInvalidCSVFile() throws Exception {
        byte[] content = new byte[10_000_000];
        new Random().nextBytes(content);
        mockMvc.perform(
                multipart(UPLOAD_URL, "personal-info")
                        .file(new MockMultipartFile("csv", content))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());

    }

    private MockMultipartFile getFakeCsvContentForEmailBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("a[a-z]{5}\\@yandex.ru"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPhoneBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("+7123456789"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForInnBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("123456"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPassportInfoBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("[0-9]{4};"));
            sb.append(fakeValuesService.regexify("123456"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForFullFilledBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            sb.append("Петров;");
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
        for (int i = 0; i < recordCount; i++) {
            sb.append("Петров;");
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{5};"));
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{7};"));
            sb.append(fakeValuesService.regexify("19[0-9]{2}-[1-9]-[1-9]"));
            sb.append("\n");
        }
        return new MockMultipartFile("csv", null,
                "text/csv", sb.toString().getBytes());
    }
}