package ruby.requestvalidation.valid.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailDTO {

    @EmailValid(require = true)
    private String requireEmail;
    @EmailValid
    private String notRequireEmail;
}
