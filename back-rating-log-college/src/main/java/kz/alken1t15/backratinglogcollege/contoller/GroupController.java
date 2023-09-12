package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.service.ServiceGroups;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {
    private final ServiceGroups serviceGroups;

    @PostMapping("/id")
    public ResponseEntity<Groups> getGroupById(@RequestBody Long id){
        return serviceGroups.findById(id);
    }

}
