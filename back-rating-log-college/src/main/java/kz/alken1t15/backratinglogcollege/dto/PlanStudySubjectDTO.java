package kz.alken1t15.backratinglogcollege.dto;

import kz.alken1t15.backratinglogcollege.entity.study.Auditorium;
import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlanStudySubjectDTO {
    private String name;
    private AuditoriumDTO auditorium;
    private LocalTime startStudy;
    private LocalTime endStudy;
}