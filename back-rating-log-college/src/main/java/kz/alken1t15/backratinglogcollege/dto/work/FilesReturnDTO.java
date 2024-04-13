package kz.alken1t15.backratinglogcollege.dto.work;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilesReturnDTO {
    private Integer currentCourse;
    private Integer currentYear;
    private List<CourseDTO> courses;
    private List<FileDTO> files;
}