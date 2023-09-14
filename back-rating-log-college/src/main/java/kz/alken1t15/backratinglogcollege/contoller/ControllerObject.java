package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.entity.Objects;
import kz.alken1t15.backratinglogcollege.service.ServiceObjects;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/object")
@AllArgsConstructor
public class ControllerObject {
    private final ServiceObjects serviceObjects;

    @PostMapping("/id")
    public ResponseEntity<Objects> getOneObject(@RequestParam Long id){
        return serviceObjects.findById(id);
    }
}
