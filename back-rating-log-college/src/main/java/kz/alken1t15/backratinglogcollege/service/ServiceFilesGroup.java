package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.work.CourseDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FileDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FileRequestDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FilesReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFilesGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceFilesGroup {
    private final RepositoryFilesGroup repositoryFilesGroup;
    private final ServiceStudents serviceStudents;


    public FilesReturnDTO getFiles(FileRequestDTO file) {
        FilesReturnDTO filesReturnDTO = new FilesReturnDTO();
        Students student = serviceStudents.getStudent();
        List<CourseDTO> courses = new ArrayList<>();
        Groups group = student.getGroup();
        for (Courses c : group.getCourses()) {
            if (file.getCourse() != null) {
                if (c.getCourse() == file.getCourse()) {
                    filesReturnDTO.setCurrentCourse(c.getCourse());
                    filesReturnDTO.setCurrentYear(c.getYear());
                }
            } else {
                if (c.getCourse() == group.getCurrentCourse()) {
                    filesReturnDTO.setCurrentCourse(c.getCourse());
                    filesReturnDTO.setCurrentYear(c.getYear());
                }
            }
            courses.add(new CourseDTO(c.getCourse(), c.getYear()));
        }
        filesReturnDTO.setCourses(courses);
        List<FilesGroup> filesGroups;
        if (file.getCourse() != null) {
            filesGroups = repositoryFilesGroup.findByCourseAndIdGroup(file.getCourse(), student.getGroup().getId());
        } else {
            filesGroups = repositoryFilesGroup.findByCourseAndIdGroup(student.getGroup().getCurrentCourse(), student.getGroup().getId());
        }
        List<FileDTO> files = new ArrayList<>();
        for (FilesGroup f : filesGroups) {
            String str = f.getDateCreate().getDayOfMonth() + " " + getMonth(f.getDateCreate().getMonthValue());
            files.add(new FileDTO(f.getId(), f.getName(), str));
        }
        filesReturnDTO.setFiles(files);
        return filesReturnDTO;
    }


    public String getMonth(int idMonth) {
        String[] arrMonth = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня", "", "", "сентября", "октября", "ноября", "декабря"};
        return arrMonth[idMonth];
    }
}