package kz.alken1t15.backratinglogcollege.contoller;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.service.ServiceStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/","/polytech"})
@AllArgsConstructor
public class MainController {
    private final ServiceStudents serviceStudents;

    @PostMapping("/id")
    public ResponseEntity<Students> getStudent(@RequestBody Long id){
        return  serviceStudents.findById(id);
    }
}
