package ruby.requestvalidation.valid.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface EmailValid {
    String MESSAGE = "이메일 형식이 올바르지 않습니다.";

    String message() default MESSAGE;

    boolean require() default false;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
