package kz.alken1t15.backratinglogcollege.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class CurrentGraphStudyGroup {
    private String name;
    private LocalTime timeStart;
    private String nameSubject;
    private Long idGroup;

    public CurrentGraphStudyGroup(String name, LocalTime timeStart, String nameSubject, Long idGroup) {
        this.name = name;
        this.timeStart = timeStart;
        this.nameSubject = nameSubject;
        this.idGroup = idGroup;
    }
}