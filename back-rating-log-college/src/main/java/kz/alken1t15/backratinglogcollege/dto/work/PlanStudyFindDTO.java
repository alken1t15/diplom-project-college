package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanStudyFindDTO {
    private String subjectName;
    private String teacherFirstName;
    private String teacherSecondName;

    public PlanStudyFindDTO(String subjectName, String teacherFirstName, String teacherSecondName) {
        this.subjectName = subjectName;
        this.teacherFirstName = teacherFirstName;
        this.teacherSecondName = teacherSecondName;
    }
}