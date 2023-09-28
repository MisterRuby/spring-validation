package ruby.requestvalidation.valid.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailPattern, String> {

    private EmailPattern constraintAnnotation;

    @Override
    public void initialize(EmailPattern constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (allowEmpty(value)) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return value.matches("^[a-zA-Z\\d_!#$%&'+/=?{|}~^.-]+@[a-zA-Z\\d.-]+$");
    }

    private boolean allowEmpty(String value) {
        return !constraintAnnotation.require() && (value == null || value.isBlank());
    }
}
