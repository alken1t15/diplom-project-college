package kz.alken1t15.backratinglogcollege.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupTotalDTO {
    private Long id;
    private Integer course;
    private Integer semester;
    private Long subject;
}
