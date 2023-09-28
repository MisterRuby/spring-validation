package ruby.requestvalidation.valid.multipart;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultipartFileValidator implements ConstraintValidator<MultipartFileValid, MultipartFile> {

    private MultipartFileValid constraintAnnotation;

    @Override
    public void initialize(MultipartFileValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (allowEmpty(multipartFile)) {
            return true;
        }

        if (multipartFile == null) {
            return false;
        }

        if (emptyFileName(multipartFile)) {
            setEmptyFileNameValidMessage(context);
            return false;
        }

        return isValidType(multipartFile);
    }

    private boolean allowEmpty(MultipartFile multipartFile) {
        return !constraintAnnotation.require() && multipartFile == null;
    }

    private boolean emptyFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return originalFilename == null || originalFilename.isBlank();
    }

    private void setEmptyFileNameValidMessage(ConstraintValidatorContext context) {
        String emptyFileNameValidMessage = "파일명은 공백이 아니어야 합니다.";
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(emptyFileNameValidMessage).addConstraintViolation();
    }

    private boolean isValidType(MultipartFile multipartFile) {
        MultipartFileType[] types = constraintAnnotation.types();
        if (types.length == 0) {
            return true;
        }

        return Arrays.stream(types)
            .anyMatch(multipartFileType -> multipartFileType.isValidType(multipartFile));
    }
}
