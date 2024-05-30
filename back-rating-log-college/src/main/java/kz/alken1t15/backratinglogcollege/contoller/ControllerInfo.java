package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.SpecializationDTO;
import kz.alken1t15.backratinglogcollege.dto.TimeStudyDTO;
import kz.alken1t15.backratinglogcollege.dto.WeekDTO;
import kz.alken1t15.backratinglogcollege.entity.Specialization;
import kz.alken1t15.backratinglogcollege.entity.study.TimeStudy;
import kz.alken1t15.backratinglogcollege.entity.study.Week;
import kz.alken1t15.backratinglogcollege.repository.RepositorySpecialization;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTimeStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryWeek;
import kz.alken1t15.backratinglogcollege.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/info")
public class ControllerInfo {
    private final ServiceGroups serviceGroups;
    private final ServiceTeachers serviceTeachers;
    private final ServiceAuditorium serviceAuditorium;
    private final RepositoryTimeStudy repositoryTimeStudy;
    private final RepositoryWeek repositoryWeek;
    private final ServiceSubject serviceSubject;
    private final RepositorySpecialization repositorySpecialization;
    private final ServiceCurator serviceCurator;

    @GetMapping("/group")
    public ResponseEntity getAllGroup() {
        return serviceGroups.findAll();
    }

    @GetMapping("/teacher")
    public ResponseEntity getAllTeacher() {
        return serviceTeachers.findAll();
    }

    @GetMapping("/auditorium")
    public ResponseEntity getAllAuditorium() {
        return serviceAuditorium.findAll();
    }

    @GetMapping("/time")
    public ResponseEntity getAllTimeStudy() {
        List<TimeStudy> timeStudies = repositoryTimeStudy.findAll();
        List<TimeStudyDTO> timeStudyDTOS = new ArrayList<>();
        for (TimeStudy timeStudy : timeStudies) {
            timeStudyDTOS.add(new TimeStudyDTO(timeStudy.getId(), timeStudy.getStartLesson(), timeStudy.getEndLesson()));
        }
        return new ResponseEntity(timeStudyDTOS, HttpStatus.OK);
    }

    @GetMapping("/week")
    public ResponseEntity getAllWeek() {
        List<Week> weeks = repositoryWeek.findAll();
        List<WeekDTO> weekDTOS = new ArrayList<>();
        for (Week week : weeks) {
            weekDTOS.add(new WeekDTO(week.getId(), week.getName()));
        }
        return new ResponseEntity(weekDTOS, HttpStatus.OK);
    }

    @GetMapping("/subject")
    public ResponseEntity getAllSubject() {
        return serviceSubject.findAll();
    }

    @GetMapping("/specialization")
    public ResponseEntity getAllSpecialization() {
        List<Specialization> list = repositorySpecialization.findAll();
        List<SpecializationDTO> specializationDTOS = new ArrayList<>();
        for (Specialization s : list) {
            specializationDTOS.add(new SpecializationDTO(s.getId(), s.getName()));
        }
        return new ResponseEntity(specializationDTOS, HttpStatus.OK);
    }

    @GetMapping("/curator")
    public ResponseEntity getAllCurator() {
        return serviceCurator.findAll();
    }

    @GetMapping("/group/about")
    public ResponseEntity getAllInfoGroup() {
        return serviceGroups.findAllInfo();
    }

    @GetMapping("/type")
    public ResponseEntity getAllTypeStudy(@RequestParam("id") Long id){
        return serviceGroups.getAllTypeStudy(id);
    }
}