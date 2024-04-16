package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.work.*;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.service.*;
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
    private final ServiceStudyProcess serviceStudyProcess;
    private final ServiceFilesGroup serviceFilesGroup;
    private final ServiceFilesStudent serviceFilesStudent;
    private final ServiceHoweWork serviceHoweWork;

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
        Students student = serviceStudents.getStudent();
        Groups groups = student.getGroup();
        Integer course = groups.getCurrentCourse();
        List<Evaluations> evaluations = serviceEvaluations.findByDateEvaluation(LocalDate.now(), student.getId());
        List<EvaluationsReturnDTO> evaluationsReturnDTOS = new ArrayList<>();
        for (Evaluations e : evaluations) {
            EvaluationsReturnDTO evaluationsReturnDTO = modelMapper.map(e, EvaluationsReturnDTO.class);
            evaluationsReturnDTOS.add(evaluationsReturnDTO);
        }
        MonthDTO monthDTO = serviceOmissions.findByMonth(userId.getNumberOfMonth(), course, student.getId());
        PlanStudyDTO planStudyDTO = servicePlanStudy.findByOfDate(groups.getId());
        List<MonthReturnDTO> months = serviceStudyProcess.getStudyProcessAll(student.getGroup().getCurrentCourse(), student.getGroup().getId());
        return new StudentInfoMainPageDTO(student.getFirstName(), student.getSecondName(), groups.getName(), groups.getYear(), evaluationsReturnDTOS, monthDTO, planStudyDTO, months);
    }

    @PostMapping(path = "/grade")
    public ProcessReturnDTO getStudyProcess(@RequestBody ProcessDTO process) {
        return serviceStudyProcess.getStudyProcess(process);
    }

    @PostMapping(path = "/courses")
    public FilesReturnDTO getFiles(@RequestBody FileRequestDTO fileDTO) {
        return serviceFilesGroup.getFiles(fileDTO);
    }

    @PostMapping(path = "/file/add")
    public ResponseEntity saveFile(@RequestBody FilesStudentRequestDTO file) {
        return serviceFilesStudent.save(file);
    }

    @PostMapping(path = "/home/task")
    public HomeWorkReturnDTO getHomeTask(@RequestBody HomeWorkRequest homeWorkRequest) {
        return serviceHoweWork.getAllHomeWordNotCompleted(homeWorkRequest);
    }
}