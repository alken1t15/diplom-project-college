package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.StudentDTO;
import kz.alken1t15.backratinglogcollege.dto.StudentInfoMainPageDTO;
import kz.alken1t15.backratinglogcollege.dto.UserId;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.service.ServiceEvaluations;
import kz.alken1t15.backratinglogcollege.service.ServiceOmissions;
import kz.alken1t15.backratinglogcollege.service.ServiceStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class ControllerStudent {
    private final ServiceStudents serviceStudents;
    private final ServiceEvaluations serviceEvaluations;
    private final ServiceOmissions serviceOmissions;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Students> getStudent(@PathVariable("id") Long id) {
        return serviceStudents.findById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity addNewGroup(@RequestBody StudentDTO student) {
        return serviceStudents.save(student);
    }

    @PostMapping(path = "/main")
    public StudentInfoMainPageDTO getInfoForMainPage(@RequestBody UserId userId) {
        Students students = serviceStudents.findByIdStudent(userId.getId());
        Groups groups = students.getGroup();
        Integer course = groups.getCourse();
        List<Evaluations> evaluations = serviceEvaluations.findByDateEvaluation(LocalDate.now());
        MonthDTO monthDTO = serviceOmissions.findByMonth(userId.getNumberOfMonth(),course);

        return new StudentInfoMainPageDTO(students.getFirstName(), students.getSecondName(), groups.getName(), groups.getYear(), evaluations,monthDTO);
    }
}