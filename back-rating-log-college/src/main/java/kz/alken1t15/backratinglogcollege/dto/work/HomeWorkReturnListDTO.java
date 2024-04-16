package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class HomeWorkReturnListDTO {
    private Long id;
    private String name;
    private String subjectName;
    private String startDate;
    private String endDate;
    private String teacherName;
}
