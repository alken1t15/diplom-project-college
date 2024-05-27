package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeWorkTeacherDTO {
    private Long idWork;
    private String name;
    private String nameSubject;
    private String groupName;
    private String dateEnd;
    private String completedCount;
}