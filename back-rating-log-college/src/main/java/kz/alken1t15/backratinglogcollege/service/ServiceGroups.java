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

    public ResponseEntity addNewStudent(ControllerGroup.StudentAndGroup studentAndGroup) {
        if (studentAndGroup.idStudent() == null || studentAndGroup.idGroup() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Students students = repositoryStudents.findById(studentAndGroup.idStudent()).orElse(null);
        Groups groups = repositoryGroups.findById(studentAndGroup.idGroup()).orElse(null);
        if (students == null || groups == null){
            System.out.println(students);
            System.out.println(groups);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            groups.getStudents().add(students);
            students.setGroup(groups);
            repositoryStudents.save(students);
            repositoryGroups.save(groups);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}