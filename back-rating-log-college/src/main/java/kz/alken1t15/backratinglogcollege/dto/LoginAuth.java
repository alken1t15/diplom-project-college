package kz.alken1t15.backratinglogcollege.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginAuth {
    private String login;
    private String password;
    private LocalDate date;
}