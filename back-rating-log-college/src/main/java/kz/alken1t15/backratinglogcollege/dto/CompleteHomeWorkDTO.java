package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteHomeWorkDTO {
    private Long idWork;
    private Long idStudent;
    private String nameWork;
    private String nameStudent;
    private String completeDate;
    private String deadlineWork;
}
