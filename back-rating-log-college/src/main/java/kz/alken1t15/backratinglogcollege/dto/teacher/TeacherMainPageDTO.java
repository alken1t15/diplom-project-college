package kz.alken1t15.backratinglogcollege.dto.teacher;

import kz.alken1t15.backratinglogcollege.service.ServiceTeachers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeacherMainPageDTO {
   private List<ServiceTeachers.CurrentGraphStudyGroup> graphGroupsForStudy;
   private List<ServiceTeachers.CurrentOmissionStudent> currentOmissionStudents;
   private ServiceTeachers.TeacherDTOP teacher;
}
