package kz.alken1t15.backratinglogcollege.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginAuth {
    private String login;
    private String password;
}