package kz.alken1t15.backratinglogcollege.contoller;


import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.entity.AuditoriumAddDTO;
import kz.alken1t15.backratinglogcollege.entity.Specialization;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.repository.RepositorySpecialization;
import kz.alken1t15.backratinglogcollege.service.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/create")
public class ControllerCreate {
    private final ServiceTeachers serviceTeachers;
    private final ServiceGroups serviceGroups;
    private final ServiceCurator serviceCurator;
    private final ServiceStudents serviceStudents;
    private final ServiceSubject serviceSubject;
    private final ServiceAuditorium serviceAuditorium;
    private final ServiceStudyProcess serviceStudyProcess;
    private final ServiceTypeStudy saveNewTypeStudy;
    private final ServicePlanStudy servicePlanStudy;
    private final RepositorySpecialization repositorySpecialization;

    @PostMapping(path = "/teacher/add")
    public ResponseEntity addTeacher(@RequestBody @Validated TeacherAddDTO teacher, BindingResult bindingResult) {
        return serviceTeachers.saveNewTeacher(teacher, bindingResult);
    }

    @PostMapping(path = "/curator/add")
    public ResponseEntity addCurator(@RequestBody @Validated CuratorAddDTO curator, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Teachers teacher = serviceTeachers.findById(curator.getIdTeacher());
        if (teacher == null) {
            return new ResponseEntity("Такого учителя нету", HttpStatus.BAD_REQUEST);
        }
        return serviceCurator.saveNewCurator(teacher);
    }

    @PostMapping(path = "/group/add")
    public ResponseEntity addGroup(@RequestBody @Validated GroupAddDTO group, BindingResult bindingResult) {
        return serviceGroups.saveNewGroup(group, bindingResult);
    }

    @PostMapping(path = "/student/add")
    public ResponseEntity addStudent(@RequestBody @Validated StudentAddDTO student, BindingResult bindingResult) {
        return serviceStudents.saveNewStudent(student, bindingResult);
    }

    @PostMapping(path = "/student/add/excel")
    public ResponseEntity addStudentExcel(@RequestParam List<MultipartFile> files, @Validated @NonNull @RequestParam("id") Long id) {
        return serviceStudents.saveNewStudentExcel(files,id);
    }

    @PostMapping(path = "/subject/add")
    public ResponseEntity addSubject(@RequestBody @Validated SubjectAddDTO subject, BindingResult bindingResult) {
        return serviceSubject.saveNewSubject(subject, bindingResult);
    }

    @PostMapping(path = "/auditorium/add")
    public ResponseEntity addAuditorium(@RequestBody @Validated AuditoriumAddDTO auditorium, BindingResult bindingResult) {
        return serviceAuditorium.saveNewAuditorium(auditorium, bindingResult);
    }

    @PostMapping(path = "/study/process/add")
    public ResponseEntity addStudyProcess(@RequestBody @Validated StudyProcessDTO studyProcess, BindingResult bindingResult) {
        return serviceStudyProcess.saveNewStudyProcess(studyProcess, bindingResult);
    }

    @PostMapping(path = "/type/study/add")
    public ResponseEntity addTypeStudy(@RequestBody @Validated TypeStudyAddDTO typeStudy, BindingResult bindingResult) {
        return saveNewTypeStudy.saveNewTypeStudy(typeStudy, bindingResult);
    }

    @PostMapping(path = "/plan/study/add")
    public ResponseEntity addPlanStydy(@RequestBody @Validated PlanStudyAddDTO planStudy, BindingResult bindingResult) {
        return servicePlanStudy.saveNewPlanStudy(planStudy, bindingResult);
    }

    @PostMapping(path = "/specialization/add")
    public ResponseEntity addSpecialization(@RequestBody @Validated SpecializationAddDTO specializationAddDTO) {
        if (StringUtils.isBlank(specializationAddDTO.getName())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Specialization specialization = repositorySpecialization.findByName(specializationAddDTO.getName());
        if (specialization!=null){
            return new ResponseEntity("Такая специальность уже есть", HttpStatus.CONFLICT);
        }
        repositorySpecialization.save(new Specialization(specializationAddDTO.getName()));
        return new ResponseEntity(HttpStatus.OK);
    }
}