package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetHomeWorkDTO {
    @NotNull
    private Long idWork;
    @NotNull
    private Long idStudent;
    private String ball;
    private Boolean repeat;
}