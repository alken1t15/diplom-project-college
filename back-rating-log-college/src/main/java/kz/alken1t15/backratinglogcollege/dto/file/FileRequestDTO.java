package kz.alken1t15.backratinglogcollege.dto.file;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FileRequestDTO {
    private Long id;
    private String name;
    private String file;
    private String typeFile;
    private LocalDate date;
}