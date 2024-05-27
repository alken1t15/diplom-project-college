package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyDTO {
    private List<GroupsStudyDTO> groupsStudyDTOS;
    private List<SubjectStudyDTO> subjectStudyDTOS;
}