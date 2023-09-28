package ruby.requestvalidation;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final String message;
    private Map<String, String> validation;

    @Builder
    public ErrorResponse(String message) {
        this.message = message;
    }

    public void addValidation(FieldError fieldError) {
        if (validation == null) this.validation = new HashMap<>();

        this.validation.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
