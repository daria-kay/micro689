package com.darakay.micro689.sources;

import com.darakay.micro689.domain.*;
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

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
public class UploadFileResourceTest {

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

        List<FullFilledBLRecord> result = fullFilledBLRepository.findBySurname("Петров");
        assertThat(result).hasSize(10);
    }

    @Test
    public void shouldUploadCsvFile_ToPersonalInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "personal-info")
                .file(getFakeCsvContentForPersonalInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        List<PersonalInfoBLRecord> result = personalInfoBLRepository.findBySurname("Петров");
        assertThat(result).hasSize(5);
    }

    @Test
    public void shouldUploadCsvFile_ToPassportInfoBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "passport-info")
                .file(getFakeCsvContentForPassportInfoBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        List<PassportInfoBLRecord> result = passportInfoBLRepository.findByPassportNumber("123456");
        assertThat(result).hasSize(5);
    }

    @Test
    public void shouldUploadCsvFile_ToInnBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "inn")
                .file(getFakeCsvContentForInnBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        List<InnBLRecord> result = innBLRepository.findByInn("123456");
        assertThat(result).hasSize(5);
    }

    @Test
    public void shouldUploadCsvFile_ToPhoneBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "phone")
                .file(getFakeCsvContentForPhoneBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        PhoneBLRecord result = phoneBLRepository.findById(5).get();
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldUploadCsvFile_ToEmailBlackList() throws Exception {
        mockMvc.perform(multipart(UPLOAD_URL, "email")
                .file(getFakeCsvContentForEmailBL(5))
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
        EmailBLRecord result = emailBLRepository.findById(5).get();
        assertThat(result).isNotNull();
    }

    private MockMultipartFile getFakeCsvContentForEmailBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("a[a-z]{5}\\@yandex.ru"));
            sb.append("\n");
        }
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPhoneBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("+7123456789"));
            sb.append("\n");
        }
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForInnBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("123456"));
            sb.append("\n");
        }
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPassportInfoBL(int recordCount) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
            sb.append(fakeValuesService.regexify("[0-9]{4};"));
            sb.append(fakeValuesService.regexify("123456"));
            sb.append("\n");
        }
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForFullFilledBL(int recordCount){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
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
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }

    private MockMultipartFile getFakeCsvContentForPersonalInfoBL(int recordCount){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recordCount; i++) {
            sb.append("Петров;");
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{5};"));
            sb.append(fakeValuesService.regexify("[А-Я]{1}[а-я]{7};"));
            sb.append(fakeValuesService.regexify("19[0-9]{2}-[1-9]-[1-9]"));
            sb.append("\n");
        }
        return new MockMultipartFile("file.csv", null,
                "text/csv", sb.toString().getBytes());
    }
}