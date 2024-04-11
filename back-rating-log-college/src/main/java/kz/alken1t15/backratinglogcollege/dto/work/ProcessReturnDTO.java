package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessReturnDTO {
    private Integer currentCourse;
    private Integer totalCourse;
    private Integer currentSemester;
    private List<MonthReturnDTO> months;
    private List<String[]> evaluations;
}