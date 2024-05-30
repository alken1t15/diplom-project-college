package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyProcessInfoDTO {
    private Long id;
    private Integer semester;
    private Integer course;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private List<TypeStudyInfoDTO> typeStudyInfo;
}