package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    private Long groupId;

    private String firstName;

    private String secondName;

    private String middleName;

    private String login;

    private String password;

    private LocalDate bornDate;
}