package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;

    private String firstName;

    private String secondName;

    private String middleName;

    private String login;

    private String password;

    private LocalDate bornDate;

}