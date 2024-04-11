package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MonthReturnDTO {
    private String name;
    private Integer requestMonth;
}