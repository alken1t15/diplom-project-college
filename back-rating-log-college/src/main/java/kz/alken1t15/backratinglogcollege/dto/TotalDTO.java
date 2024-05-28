package kz.alken1t15.backratinglogcollege.dto;

import kz.alken1t15.backratinglogcollege.dto.work.PlanStudyFindDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TotalDTO {
    private String[][] resultArray;
    private List<List<PlanStudyFindDTO>> teachers;
}