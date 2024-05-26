package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupAddDTO {
    @NotNull
    private Long idCurator;
    @NotNull
    private String name;
    @NotNull
    private Long idSpecialization;
}