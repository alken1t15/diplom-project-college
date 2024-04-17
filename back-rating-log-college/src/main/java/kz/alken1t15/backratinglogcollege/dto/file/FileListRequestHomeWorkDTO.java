package kz.alken1t15.backratinglogcollege.dto.file;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileListRequestHomeWorkDTO {
    private Long idHomeTask;
    private List<FileRequestDTO> files;
}