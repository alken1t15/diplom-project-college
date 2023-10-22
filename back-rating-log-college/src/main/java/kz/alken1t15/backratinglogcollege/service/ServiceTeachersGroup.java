package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeachersGroup;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.entity.TeachersGroup;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachersGroup;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceTeachersGroup {
    private RepositoryTeachersGroup repositoryTeachersGroup;
    private RepositoryTeachers repositoryTeachers;
    private RepositoryGroups repositoryGroups;

    public ResponseEntity save(ControllerTeachersGroup.TeachersGroupTemp teachersGroupTemp) {
        if (teachersGroupTemp.idTeacher() == null || teachersGroupTemp.idGroup() == null || StringUtils.isBlank(teachersGroupTemp.name()) ||
        teachersGroupTemp.course() == null || teachersGroupTemp.semester() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            Teachers teachers = repositoryTeachers.findById(teachersGroupTemp.idTeacher()).orElse(null);
            Groups groups = repositoryGroups.findById(teachersGroupTemp.idGroup()).orElse(null);
            TeachersGroup teachersGroup = repositoryTeachersGroup.findByNameObjectAndCourseAndSemester(teachersGroupTemp.name(),(long) teachersGroupTemp.course(),(long) teachersGroupTemp.semester()).orElse(null);
            if (teachers == null || groups == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            else if (teachersGroup != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            else {
                repositoryTeachersGroup.save(new TeachersGroup(teachers,groups,teachersGroupTemp.name(), (long) teachersGroupTemp.course(), (long) teachersGroupTemp.semester()));
                return ResponseEntity.status(HttpStatus.OK).build();
            }

        }
    }
}
