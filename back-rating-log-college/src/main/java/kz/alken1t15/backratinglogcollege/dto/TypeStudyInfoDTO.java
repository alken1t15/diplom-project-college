package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeStudyInfoDTO {
    private Long id;
    private String name;
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
