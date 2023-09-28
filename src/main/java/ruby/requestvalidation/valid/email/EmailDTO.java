package ruby.requestvalidation.valid.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailDTO {

    @EmailPattern(require = true)
    private String requireEmail;
    @EmailPattern
    private String notRequireEmail;
}
