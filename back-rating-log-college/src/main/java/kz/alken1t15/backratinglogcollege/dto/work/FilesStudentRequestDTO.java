package kz.alken1t15.backratinglogcollege.dto.work;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilesStudentRequestDTO {
    private String name;
    private String file;
}