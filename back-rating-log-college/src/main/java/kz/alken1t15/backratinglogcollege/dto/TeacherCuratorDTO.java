package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCuratorDTO {
    private Long id;

    private String firstName;

    private String secondName;

    private String middleName;
}