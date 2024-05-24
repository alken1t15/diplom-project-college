package kz.alken1t15.backratinglogcollege.dto.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAddDTO {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String secondName;
    private String middleName;
    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    private LocalDate bornDate;
    @NotNull
    private LocalDate startWork;
}