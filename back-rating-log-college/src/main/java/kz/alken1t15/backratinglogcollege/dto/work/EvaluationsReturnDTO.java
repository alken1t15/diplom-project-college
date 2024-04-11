package kz.alken1t15.backratinglogcollege.dto.work;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EvaluationsReturnDTO {
    private String nameObject;
    private LocalDate dateEvaluation;
    private Long ball;
    private String nameTeacher;
}