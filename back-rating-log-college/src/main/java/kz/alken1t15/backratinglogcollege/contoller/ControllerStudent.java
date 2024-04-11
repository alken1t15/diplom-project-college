package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.work.EvaluationsReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.service.ServiceEvaluations;
import kz.alken1t15.backratinglogcollege.service.ServiceOmissions;
import kz.alken1t15.backratinglogcollege.service.ServicePlanStudy;
import kz.alken1t15.backratinglogcollege.service.ServiceStudents;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ServicePlanStudy servicePlanStudy;

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<Students> getStudent(@PathVariable("id") Long id) {
//        return serviceStudents.findById(id);
//    }
//
//    @PostMapping(path = "/add")
//    public ResponseEntity addNewGroup(@RequestBody StudentDTO student) {
//        return serviceStudents.save(student);
//    }

    @PostMapping(path = "/main")
    public StudentInfoMainPageDTO getInfoForMainPage(@RequestBody UserId userId) {
        ModelMapper modelMapper = new ModelMapper();
        Students students = serviceStudents.getStudent();
        Groups groups = students.getGroup();
        Integer course = groups.getCurrentCourse();
        List<Evaluations> evaluations = serviceEvaluations.findByDateEvaluation(LocalDate.now(), students.getId());
        List<EvaluationsReturnDTO> evaluationsReturnDTOS = new ArrayList<>();
        for (Evaluations e : evaluations) {
            EvaluationsReturnDTO evaluationsReturnDTO = modelMapper.map(e, EvaluationsReturnDTO.class);
            evaluationsReturnDTOS.add(evaluationsReturnDTO);
        }
        MonthDTO monthDTO = serviceOmissions.findByMonth(userId.getNumberOfMonth(), course, students.getId());
        PlanStudyDTO planStudyDTO = servicePlanStudy.findByOfDate(groups.getId());

        return new StudentInfoMainPageDTO(students.getFirstName(), students.getSecondName(), groups.getName(), groups.getYear(), evaluationsReturnDTOS, monthDTO, planStudyDTO);
    }
}