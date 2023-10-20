package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.service.ServiceTeachers;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class ControllerTeacher {
    private final ServiceTeachers serviceTeachers;

    @PostMapping(path = "/add")
    public HttpStatus addEvaluation(@RequestBody Teacher teacher){
        return serviceTeachers.save(teacher);
    }

    public record Teacher(String firstName, String secondName, String middleName, String login, String password, String bornDate){}
}
