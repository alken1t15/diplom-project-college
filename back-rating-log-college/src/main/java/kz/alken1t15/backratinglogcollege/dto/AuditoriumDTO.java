package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuditoriumDTO {
    private Integer block;
    private Integer floor;
    private Integer cabinet;
}