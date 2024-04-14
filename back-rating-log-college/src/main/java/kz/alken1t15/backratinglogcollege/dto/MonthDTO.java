package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MonthDTO  {
    private String nameMonth;
    private List<DayDTO> days;

}