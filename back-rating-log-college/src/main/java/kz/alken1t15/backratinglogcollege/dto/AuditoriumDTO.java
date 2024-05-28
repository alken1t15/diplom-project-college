package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriumDTO {
    private Long id;
    private Integer block;
    private Integer floor;
    private Integer cabinet;

    public AuditoriumDTO(Integer block, Integer floor, Integer cabinet) {
        this.block = block;
        this.floor = floor;
        this.cabinet = cabinet;
    }
}