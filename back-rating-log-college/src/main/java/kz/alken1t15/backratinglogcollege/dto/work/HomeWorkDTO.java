package kz.alken1t15.backratinglogcollege.dto.work;


import kz.alken1t15.backratinglogcollege.dto.FileHomeTaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HomeWorkDTO {
    private Long id;
    private String name;
    private String subjectName;
    private String startDate;
    private String endDate;
    private String teacherName;
    private String description;
    private String statusStudent;
    private List<FileHomeTaskDTO> fileHomeTask;
    private List<byte[]> files;
}