package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;

    public ResponseEntity<Students> findById(Long id) {
        Students students = repositoryStudents.findById(id).orElse(null);
        if (students == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            return new ResponseEntity<>(students,HttpStatus.OK);
        }
    }
}
