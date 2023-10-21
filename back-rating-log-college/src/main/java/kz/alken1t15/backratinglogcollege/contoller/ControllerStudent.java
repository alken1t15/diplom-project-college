package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.Student;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.service.ServiceStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class ControllerStudent {
    private final ServiceStudents serviceStudents;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Students> getStudent(@PathVariable("id") Long id) {
        return serviceStudents.findById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity addNewGroup(@RequestBody Student student) {
        return serviceStudents.save(student);
    }
}