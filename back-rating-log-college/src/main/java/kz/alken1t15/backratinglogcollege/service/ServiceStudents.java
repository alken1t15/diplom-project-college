package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.StudentDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;
    private final RepositoryGroups repositoryGroups;
    private final RepositoryUser repositoryUser;

    public ResponseEntity<Students> findById(Long id) {
        Students students = repositoryStudents.findById(id).orElse(null);
        if (students == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
    }

    //TODO Добавить проверку на уникальность студента мб через ИИН или другой вариант
    public ResponseEntity save(StudentDTO studentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Students students = modelMapper.map(studentDTO, Students.class);
        Groups groups = repositoryGroups.findById(studentDTO.getGroupId()).orElse(null);
        if (groups != null) {
            if (StringUtils.isBlank(students.getFirstName()) || StringUtils.isBlank(students.getSecondName()) || students.getBornDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                students.setGroup(groups);
                repositoryStudents.save(students);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public Students getStudent() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = repositoryUser.findByLogin(securityContext.getAuthentication().getName()).orElseThrow();
        return repositoryStudents.findById(user.getId()).orElseThrow();
    }


    public Students findByIdStudent(Long id) {
        return repositoryStudents.findByIdCustom(id).orElseThrow();
    }

}