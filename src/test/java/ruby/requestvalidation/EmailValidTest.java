package ruby.requestvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ruby.requestvalidation.valid.email.EmailDTO;
import ruby.requestvalidation.valid.email.EmailValid;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmailValidTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("이메일 형식의 값이 필수인 필드에 값이 없을 경우")
    void validNullRequireEmail() throws Exception {
        EmailDTO emailDTO = new EmailDTO();

        mockMvc.perform(post("/valid/email")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireEmail").value(EmailValid.MESSAGE));
    }

    @Test
    @DisplayName("이메일 형식의 값이 필수인 필드에 값이 빈 값일 경우")
    void validEmptyRequireEmail() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRequireEmail("");

        mockMvc.perform(post("/valid/email")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireEmail").value(EmailValid.MESSAGE));
    }

    @Test
    @DisplayName("이메일 형식의 값이 아닐 경우 체크")
    void validWrongEmailPattern() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRequireEmail("qwe.com");
        emailDTO.setNotRequireEmail("qsad");

        mockMvc.perform(post("/valid/email")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validation.requireEmail").value(EmailValid.MESSAGE))
            .andExpect(jsonPath("$.validation.notRequireEmail").value(EmailValid.MESSAGE));
    }

    @Test
    @DisplayName("이메일 형식의 값일 경우 정상 체크")
    void validRightEmailPattern() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRequireEmail("qwe@naver.com");
        emailDTO.setNotRequireEmail("qsad@gmail.com");

        mockMvc.perform(post("/valid/email")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isOk());
    }
}