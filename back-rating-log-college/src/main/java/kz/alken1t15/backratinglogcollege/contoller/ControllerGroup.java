package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.GroupDTO;
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

//    @GetMapping("/{id}")
//    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
//        return serviceGroups.findById(id);
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity addNewGroup(@RequestBody Group group) {
//        return serviceGroups.save(group);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<GroupDTO>> getAllGroups() {
//        return new ResponseEntity<List<GroupDTO>>(serviceGroups.findAll(), HttpStatus.OK);
//    }
//
//    @PostMapping("/add/student")
//    public ResponseEntity addNewStudentInGroup(@RequestBody StudentAndGroup studentAndGroup) {
//        return serviceGroups.addNewStudent(studentAndGroup);
//    }
//
//    public record StudentAndGroup(Long idStudent, Long idGroup){}
//
//    public record Group(Long id, String name){}

}