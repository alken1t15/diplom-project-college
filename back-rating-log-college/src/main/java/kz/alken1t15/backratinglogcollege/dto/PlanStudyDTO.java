package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlanStudyDTO {
    private String date;
    private String nameOfWeek;
    private List<PlanStudySubjectDTO> subjects;
}