package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {
    private Integer id;
    private String name;
    private String date;
    private byte[] file;
    private String subjectName;
    private String description;
}