package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {
    private Long id;
    private String name;
    private String date;
}