package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeWorkStudentDTO {
    private Long idWork;
    private String name;
    private String nameSubject;
    private String groupName;
    private String dateEnd;
    private String completedCount;
    private List<StudentHomeWorkDTO> students;
}