package kz.alken1t15.backratinglogcollege.dto;

import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {

    private Long groupId;

    private String firstName;

    private String secondName;

    private String middleName;

    private String login;

    private String password;

    private LocalDate bornDate;

    private List<EvaluationsDTO> evaluations;
}