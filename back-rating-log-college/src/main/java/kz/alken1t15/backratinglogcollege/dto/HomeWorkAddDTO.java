package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HomeWorkAddDTO {
    @NotNull
    private Long idGroup;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String nameSubject;
}