package kz.alken1t15.backratinglogcollege.contoller;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.entity.Objects;
import kz.alken1t15.backratinglogcollege.service.ServiceObjects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/object")
@AllArgsConstructor
public class ControllerObject {
    private final ServiceObjects serviceObjects;

    @GetMapping("/{id}")
    public ResponseEntity<Objects> getOneObject(@PathVariable Long id){
        return serviceObjects.findById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Objects>> getAllObject(){
        return serviceObjects.getAllObject();
    }

    @GetMapping("/add")
    public ResponseEntity addNewObject(@RequestParam String name){
        return  serviceObjects.save(name);
    }
}