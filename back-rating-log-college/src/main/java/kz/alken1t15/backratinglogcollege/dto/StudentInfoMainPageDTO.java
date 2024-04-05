package kz.alken1t15.backratinglogcollege.dto;

import kz.alken1t15.backratinglogcollege.entity.Evaluations;
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
    private List<Evaluations> evaluations;
    private MonthDTO monthDTO;
}
