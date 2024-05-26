package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanStudyAddDTO {
    @NotNull
    private Long idTypeStudy;
    @NotNull
    private Long idTimeStudy;
    @NotNull
    private Long idSubject;
    @NotNull
    private Long idTeacher;
    @NotNull
    private Long auditorium;
    @NotNull
    private Long idWeek;
    @NotNull
    private Integer numberOfCouple;
}
