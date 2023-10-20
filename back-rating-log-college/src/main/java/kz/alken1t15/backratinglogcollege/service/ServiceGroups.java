package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerGroup;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;
    private final RepositoryStudents repositoryStudents;

    public ResponseEntity<Groups> findById(Long id) {
        Groups groups = repositoryGroups.findById(id).orElse(null);
        if (groups == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
    }

    public HttpStatus save(String name) {
        Groups groups;
        if (!StringUtils.isBlank(name)) {
            groups = repositoryGroups.findByName(name).orElse(null);

            if (groups == null) {
                repositoryGroups.save(new Groups(name));
                return HttpStatus.OK;
            } else {
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    public List<Groups> findAll() {
        return repositoryGroups.findAll();
    }

    public HttpStatus addNewStudent(ControllerGroup.StudentAndGroup studentAndGroup) {
        if (StringUtils.isBlank(studentAndGroup.idStudent()) || StringUtils.isBlank(studentAndGroup.idGroup())){
            return HttpStatus.BAD_REQUEST;
        }
        Students students = repositoryStudents.findById(Long.parseLong(studentAndGroup.idStudent())).orElse(null);
        Groups groups = repositoryGroups.findById(Long.parseLong(studentAndGroup.idGroup())).orElse(null);
        if (students == null || groups == null){
            System.out.println(students);
            System.out.println(groups);
            return HttpStatus.FORBIDDEN;
        }
        else {
            groups.getStudents().add(students);
            students.setGroup(groups);
            repositoryStudents.save(students);
            repositoryGroups.save(groups);
            return HttpStatus.OK;
        }
    }
}