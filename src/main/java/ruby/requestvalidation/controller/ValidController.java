package ruby.requestvalidation.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ruby.requestvalidation.valid.email.EmailDTO;
import ruby.requestvalidation.valid.multipart.multiple.MultipartFileMultipleDTO;
import ruby.requestvalidation.valid.multipart.single.MultipartFileDTO;

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

    @PostMapping("/multipartFile")
    @ResponseStatus(HttpStatus.OK)
    public String multipartFile(@Valid MultipartFileDTO multipartFileDTO) {
        log.info("getRequireFile.filename : {}", multipartFileDTO.getRequireFile().getOriginalFilename());
        return multipartFileDTO.getRequireFile().getOriginalFilename();
    }

    @PostMapping("/multipartFile/multiple")
    @ResponseStatus(HttpStatus.OK)
    public Integer multipartFileMultiple(@Valid MultipartFileMultipleDTO multipleDTO) {
        log.info("getRequireFiles.size : {}", multipleDTO.getRequireFiles().size());
        return multipleDTO.getRequireFiles().size();
    }
}
