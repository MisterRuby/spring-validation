package ruby.requestvalidation.valid.multipart.single;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import static ruby.requestvalidation.valid.multipart.MultipartFileType.*;

@Setter
@Getter
@NoArgsConstructor
public class MultipartFileDTO implements Serializable {
    @MultipartFileValid(require = true, types = {IMAGE, PDF})
    private MultipartFile requireFile;
    @MultipartFileValid(types = {EXCEL})
    private MultipartFile notRequireFile;
}
