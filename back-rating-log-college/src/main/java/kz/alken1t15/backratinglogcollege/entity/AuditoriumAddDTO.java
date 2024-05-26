package kz.alken1t15.backratinglogcollege.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriumAddDTO {
    @NotNull
    private Integer block;
    @NotNull
    private Integer flour;
    @NotNull
    private Integer cabinet;
}