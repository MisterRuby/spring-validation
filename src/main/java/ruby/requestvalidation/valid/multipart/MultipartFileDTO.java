package ruby.requestvalidation.valid.multipart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static ruby.requestvalidation.valid.multipart.MultipartFileType.*;

@Setter
@Getter
@NoArgsConstructor
public class MultipartFileDTO {
    @MultipartFileValid(require = true, types = {IMAGE, PDF})
    private MultipartFile requireFile;
    @MultipartFileValid(types = {EXCEL})
    private MultipartFile notRequireFile;
}
