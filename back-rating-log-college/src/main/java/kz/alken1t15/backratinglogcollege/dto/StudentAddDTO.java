package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
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
public class StudentAddDTO {
    @NotNull
    private Long groupId;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    private String middleName;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private LocalDate bornDate;
    @NotNull
    private String subgroupName;
}