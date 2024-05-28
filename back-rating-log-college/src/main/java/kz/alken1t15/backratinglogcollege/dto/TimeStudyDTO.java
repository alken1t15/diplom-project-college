package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeStudyDTO {
    private Long id;
    private LocalTime startLesson;
    private LocalTime endLesson;
}