package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HomeWorkReturnDTO {
    private List<HomeWorkReturnListDTO> homeWorks;
    private HomeWorkDTO homeWork;
}