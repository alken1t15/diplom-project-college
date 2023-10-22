package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.StudentDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;
    private final RepositoryGroups repositoryGroups;

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
            if (StringUtils.isBlank(students.getFirstName()) || StringUtils.isBlank(students.getSecondName())
                    || StringUtils.isBlank(students.getLogin())
                    || StringUtils.isBlank(students.getPassword()) || students.getBornDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                students.setGroup(groups);
                repositoryStudents.save(students);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}