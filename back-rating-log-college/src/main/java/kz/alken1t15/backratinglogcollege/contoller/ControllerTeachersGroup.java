package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.service.ServiceTeachersGroup;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers/group")
public class ControllerTeachersGroup {
    private final ServiceTeachersGroup serviceTeachersGroup;

    @PostMapping("/add")
    public ResponseEntity addNewTeachersGroup(@RequestBody TeachersGroupTemp teachersGroupTemp){
      return  serviceTeachersGroup.save(teachersGroupTemp);
    }

    public record TeachersGroupTemp(Long idTeacher, Long idGroup, String name, Integer course, Integer semester){}
}