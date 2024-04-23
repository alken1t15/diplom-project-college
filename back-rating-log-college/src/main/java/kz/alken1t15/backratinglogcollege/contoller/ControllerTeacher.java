package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.service.ServiceFilesGroup;
import kz.alken1t15.backratinglogcollege.service.ServiceHoweWork;
import kz.alken1t15.backratinglogcollege.service.ServiceTeachers;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class ControllerTeacher {
    private final ServiceTeachers serviceTeachers;
    private final ServiceFilesGroup serviceFilesGroup;
    private final ServiceHoweWork serviceHoweWork;

//    @PostMapping(path = "/add")
//    public ResponseEntity addEvaluation(@RequestBody Teacher teacher){
//        return serviceTeachers.save(teacher);
//    }

    @PostMapping(path = "/home/add")
    public ResponseEntity addNewFileHomeTaskTeacher(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id) {
        return serviceHoweWork.addNewFileHomeTaskTeacher(files,id);
    }

    @PostMapping(path = "/courses/add")
    public ResponseEntity addNewFile(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id) {
        return serviceFilesGroup.addNewFile(files,id);
    }

    public record Teacher(String firstName, String secondName, String middleName, String login, String password, String bornDate){}
}
