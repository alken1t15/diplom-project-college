package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.StudentAddDTO;
import kz.alken1t15.backratinglogcollege.dto.StudentDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudentCourse;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;
    private final RepositoryGroups repositoryGroups;
    private final ServiceUsers serviceUser;
    private final RepositoryStudentCourse repositoryStudentCourse;


    public ResponseEntity<Students> findById(Long id) {
        Students students = repositoryStudents.findById(id).orElse(null);
        if (students == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
    }

    public Students getStudent() {
        User user = serviceUser.getUser();
        return repositoryStudents.findById(user.getId()).orElseThrow();
    }


    public Students findByIdStudent(Long id) {
        return repositoryStudents.findByIdCustom(id).orElseThrow();
    }

    public ResponseEntity saveNewStudent(StudentAddDTO student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups group = repositoryGroups.findById(student.getGroupId()).orElse(null);
        if (group==null){
            return new ResponseEntity("Такой группы нету",HttpStatus.BAD_REQUEST);
        }
        Long id = serviceUser.save(student.getLogin(),student.getPassword(),"student");
       Students students = repositoryStudents.save(new Students(id,group,student.getFirstName(),student.getSecondName(),student.getMiddleName(),student.getBornDate(),student.getSubgroupName()));
       repositoryStudentCourse.save(new StudentsCourse(students,1));
        return  new ResponseEntity(HttpStatus.OK);
    }
}