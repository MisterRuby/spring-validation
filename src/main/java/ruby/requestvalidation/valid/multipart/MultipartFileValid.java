package ruby.requestvalidation.valid.multipart;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = MultipartFileValidator.class)
@Documented
public @interface MultipartFileValid {
    String MESSAGE = "파일 형식이 올바르지 않습니다.";

    String message() default MESSAGE;

    boolean require() default false;

    MultipartFileType[] types() default {};

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
