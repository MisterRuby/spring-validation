package ruby.requestvalidation.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ruby.requestvalidation.valid.email.EmailDTO;

@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidController {

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public String email(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("getRequireEmail : {}", emailDTO.getRequireEmail());
        log.info("getNotRequireEmail : {}", emailDTO.getNotRequireEmail());
        return emailDTO.getRequireEmail();
    }
}
