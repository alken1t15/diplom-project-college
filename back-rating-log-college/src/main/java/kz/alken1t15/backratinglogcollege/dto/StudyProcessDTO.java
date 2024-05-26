package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudyProcessDTO {
    @NotNull
    private Long idGroup;
    @NotNull
    private Integer semester;
    @NotNull
    private Integer course;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
}