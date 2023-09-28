package ruby.requestvalidation.valid.multipart.multiple;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ruby.requestvalidation.valid.multipart.MultipartFileType;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultipartFileMultipleValidator implements ConstraintValidator<MultipartFileMultipleValid, List<MultipartFile>> {

    private MultipartFileMultipleValid constraintAnnotation;

    @Override
    public void initialize(MultipartFileMultipleValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext context) {
        if (allowEmpty(multipartFiles)) {
            return true;
        }

        if (isEmpty(multipartFiles)) {
            return false;
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (emptyFileName(multipartFile)) {
                setEmptyFileNameValidMessage(context);
                return false;
            }

            if (!isValidType(multipartFile)) {
                return false;
            }
        }

        return true;
    }

    private boolean allowEmpty(List<MultipartFile> multipartFiles) {
        return !constraintAnnotation.require() && isEmpty(multipartFiles);
    }

    private boolean isEmpty(List<MultipartFile> multipartFiles) {
        return multipartFiles == null || multipartFiles.isEmpty();
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
