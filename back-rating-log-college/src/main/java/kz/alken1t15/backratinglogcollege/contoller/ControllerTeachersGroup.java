package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.entity.TeachersGroup;
import kz.alken1t15.backratinglogcollege.service.ServiceTeachersGroup;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers/group")
public class ControllerTeachersGroup {
    private final ServiceTeachersGroup serviceTeachersGroup;

    @PostMapping("/add")
    public ResponseEntity addNewTeachersGroup(@RequestBody TeachersGroupTemp teachersGroupTemp){
      return  serviceTeachersGroup.save(teachersGroupTemp);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TeachersGroup>> getTeachersGroup(@RequestParam String name){
        return serviceTeachersGroup.findByName(name);
    }

    public record TeachersGroupTemp(Long idTeacher, Long idGroup, String name, Integer course, Integer semester){}
}