package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.service.ServiceGroups;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class ControllerGroup {
    private final ServiceGroups serviceGroups;

    @PostMapping("/id")
    public ResponseEntity<Groups> getGroupById(@RequestBody Long id) {
        return serviceGroups.findById(id);
    }

    @PostMapping("/add")
    public HttpStatus addNewGroup(@RequestBody String name) {
        return serviceGroups.save(name);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Groups>> getAllGroups() {
        return new ResponseEntity<List<Groups>>(serviceGroups.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add/student")
    public HttpStatus addNewStudentInGroup(@RequestBody StudentAndGroup studentAndGroup) {
        return serviceGroups.addNewStudent(studentAndGroup);
    }

    public record StudentAndGroup(String idStudent, String idGroup){}

}