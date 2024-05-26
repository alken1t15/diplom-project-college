package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TypeStudyAddDTO {
    @NotNull
    private Long idStudyProcess;
    @NotNull
    private String name;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
}