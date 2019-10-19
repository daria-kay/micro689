package com.darakay.micro689.services.blacklist;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BaseBLServiceTest {

    private BaseBLService baseBLService;

    @Before
    public void setUp(){
        baseBLService = mock(BaseBLService.class);
        doCallRealMethod().when(baseBLService).parseAndSaveCSVFile(any(MultipartFile.class));
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            List<String> fake = StreamSupport
                    .stream(((Iterable<CSVRecord>)args[0]).spliterator(), false).map(r -> r.toString())
                    .collect(Collectors.toList());
            return new Object();
        }).when(baseBLService).storeRecords(any());
    }

    @Test
    public void parseAndSave_shouldCallMethod_StoreRecords() {
        MultipartFile testFile = new MockMultipartFile("csv", "file-name.csv", "text/csv",
                "Иванов;Иван;Иванович;1987-07-07;1234;123456;+7895634782;asd@asd.ru\n".getBytes());

        baseBLService.parseAndSaveCSVFile(testFile);

        verify(baseBLService, times(1)).storeRecords(any());
    }

    @Test
    public void parseAndSave_shouldNotThrowException_WhenFileContentIsInvaid() {
        MultipartFile testFile = new MockMultipartFile("csv", "file-name.csv", "text/csv",
                "ncdjnjd\"c\"d,k\"j6+789;5634782,asd@asd.ru\n".getBytes());

        try {
            baseBLService.parseAndSaveCSVFile(testFile);
        } catch (Exception e){
            fail("Exception was thrown!");
        }
    }
}