package ruby.requestvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ruby.requestvalidation.valid.multipart.multiple.MultipartFileMultipleValid;
import ruby.requestvalidation.valid.multipart.single.MultipartFileValid;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MultipartValidTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ResourceLoader resourceLoader;

    @Test
    @DisplayName("단일 파일 전송시 파일 명이 빈 값일 경우")
    void validEmptyFilenameSingleRequireFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName = "requireFile";
        String originalFilename = "";
        String contentType = "image/png";
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fieldName, originalFilename, contentType, fileInputStream);

        mockMvc.perform(multipart("/valid/multipartFile")
                .file(mockMultipartFile)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireFile").value(MultipartFileValid.FILENAME_EMPTY_MESSAGE));
    }

    @Test
    @DisplayName("단일 파일 전송시 파일 타입이 잘못된 경우")
    void validWrongFileTypeSingleRequireFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName = "requireFile";
        String originalFilename = "imageSample.jpg";
        String contentType = "application/vnd.ms-excel";
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fieldName, originalFilename, contentType, fileInputStream);

        mockMvc.perform(multipart("/valid/multipartFile")
                .file(mockMultipartFile)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireFile").value(MultipartFileValid.MESSAGE));
    }

    @Test
    @DisplayName("단일 파일 전송시 정상 체크")
    void validSingleRequireFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName = "requireFile";
        String originalFilename = "imageSample.jpg";
        String contentType = "image/png";
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fieldName, originalFilename, contentType, fileInputStream);

        mockMvc.perform(multipart("/valid/multipartFile")
                .file(mockMultipartFile)
            )
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멀티 파일 전송시 파일 명이 빈 값인 파일이 있는 경우")
    void validEmptyFilenameMultipleRequireFiles() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file1 = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName1 = "requireFiles";
        String originalFilename1 = "rybvy.png";
        String contentType1 = "image/png";
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(fieldName1, originalFilename1, contentType1, fileInputStream1);

        File file2 = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName2 = "requireFiles";
        String originalFilename2 = "";
        String contentType2 = "image/png";
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(fieldName2, originalFilename2, contentType2, fileInputStream2);

        mockMvc.perform(multipart("/valid/multipartFile/multiple")
                .file(mockMultipartFile1)
                .file(mockMultipartFile2)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireFiles").value(MultipartFileMultipleValid.FILENAME_EMPTY_MESSAGE));
    }

    @Test
    @DisplayName("멀티 파일 전송시 파일 타입이 잘못된 경우")
    void validWrongTypeMultipleRequireFiles() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file1 = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName1 = "requireFiles";
        String originalFilename1 = "rybvy.png";
        String contentType1 = "image/png";
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(fieldName1, originalFilename1, contentType1, fileInputStream1);

        File file2 = new File(resource.getFile().getAbsolutePath() + "/excel/excelSample.xlsx");
        String fieldName2 = "requireFiles";
        String originalFilename2 = "qwe.xlsx";
        String contentType2 = "application/vnd.ms-excel";
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(fieldName2, originalFilename2, contentType2, fileInputStream2);

        mockMvc.perform(multipart("/valid/multipartFile/multiple")
                .file(mockMultipartFile1)
                .file(mockMultipartFile2)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireFiles").value(MultipartFileMultipleValid.MESSAGE));
    }

    @Test
    @DisplayName("멀티 파일 전송시 정상 체크")
    void validMultipleRequireFiles() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static");
        File file1 = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName1 = "requireFiles";
        String originalFilename1 = "rybvy.png";
        String contentType1 = "image/png";
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(fieldName1, originalFilename1, contentType1, fileInputStream1);

        File file2 = new File(resource.getFile().getAbsolutePath() + "/img/imageSample.jpg");
        String fieldName2 = "requireFiles";
        String originalFilename2 = "rybvy2.png";
        String contentType2 = "image/png";
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(fieldName2, originalFilename2, contentType2, fileInputStream2);

        mockMvc.perform(multipart("/valid/multipartFile/multiple")
                .file(mockMultipartFile1)
                .file(mockMultipartFile2)
            )
            .andExpect(status().isOk());
    }
}