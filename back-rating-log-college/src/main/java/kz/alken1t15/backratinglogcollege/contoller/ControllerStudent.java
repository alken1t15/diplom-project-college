package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.work.*;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.service.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
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
    private final ServiceTotal serviceTotal;
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
        FilesStudentDTO filesStudentDTO = serviceFilesStudent.getCertificateUserToday();
        MonthDTO monthDTO = serviceOmissions.findByMonth(userId.getNumberOfMonth(), course, student.getId());
        PlanStudyDTO planStudyDTO = servicePlanStudy.findByOfDate(groups.getId());
        List<MonthReturnDTO> months = serviceStudyProcess.getStudyProcessAll(student.getGroup().getCurrentCourse(), student.getGroup().getId());
        return new StudentInfoMainPageDTO(student.getFirstName(), student.getSecondName(), groups.getName(), groups.getYear(), evaluationsReturnDTOS, monthDTO, planStudyDTO, months,filesStudentDTO);
    }

    @PostMapping(path = "/grade")
    public ProcessReturnDTO getStudyProcess(@RequestBody ProcessDTO process) {
        return serviceStudyProcess.getStudyProcess(process);
    }

    @PostMapping(path = "/courses")
    public FilesReturnDTO getFiles(@RequestBody GetFileForCourseAndIdFileDTO file) {
        return serviceFilesGroup.getFiles(file);
    }

    @PostMapping(path = "/certificate/add")
    public ResponseEntity saveFileCertificate(@RequestParam MultipartFile file) {
        return serviceFilesStudent.saveCertificate(file);
    }

    @PostMapping(path = "/home/complete")
    public ResponseEntity setCompleteHome(@Validated @RequestBody CompleteHomeTaskDTO completeHomeTaskDTO, BindingResult bindingResult){
        return  serviceHoweWork.setCompleteHome(completeHomeTaskDTO,bindingResult);
    }


    @PostMapping(path = "/home/task")
    public HomeWorkReturnDTO getHomeTask(@RequestBody HomeWorkRequest homeWorkRequest) {
        return serviceHoweWork.getAllHomeWordNotCompleted(homeWorkRequest);
    }


    @PostMapping(path = "/home/add")
    public ResponseEntity addFileForHomeTask(@RequestParam List<MultipartFile> files,@Validated @NonNull @RequestParam Long id) {
        return serviceHoweWork.addNewFileHomeTask(files,id);
    }


    @PostMapping(path = "/total")
    public ResponseEntity getTotalEvalution(){
        return serviceTotal.getTotalEvaluations();
    }
}