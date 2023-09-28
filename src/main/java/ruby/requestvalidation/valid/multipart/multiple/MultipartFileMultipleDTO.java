package ruby.requestvalidation.valid.multipart.multiple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static ruby.requestvalidation.valid.multipart.MultipartFileType.*;

@Setter
@Getter
@NoArgsConstructor
public class MultipartFileMultipleDTO {
    @MultipartFileMultipleValid(require = true, types = {IMAGE, PDF})
    private List<MultipartFile> requireFiles;
    @MultipartFileMultipleValid(types = {EXCEL})
    private List<MultipartFile> notRequireFiles;
}
