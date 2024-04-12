package kz.alken1t15.backratinglogcollege.dto;

import kz.alken1t15.backratinglogcollege.dto.work.EvaluationsReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.MonthReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudentInfoMainPageDTO {
    private String name;
    private String lastName;
    private String groupName;
    private Integer yearGroup;
    private List<EvaluationsReturnDTO> evaluations;
    private MonthDTO monthDTO;
    private PlanStudyDTO planStudyDTO;
    private List<MonthReturnDTO> monthReturnDTOS;
}