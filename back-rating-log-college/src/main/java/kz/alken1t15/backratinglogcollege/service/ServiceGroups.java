package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup;
import kz.alken1t15.backratinglogcollege.entity.Curator;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Specialization;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;
    private final ServiceCurator serviceCurator;
    private final ServiceSpecialization serviceSpecialization;
    private final ServiceCourses serviceCourses;

    public List<CurrentGraphStudyGroup> findByAllGroupForTeacher(Long idTeacher, LocalDate date, Long idWeek) {
        return repositoryGroups.findByAllGroupForTeacher(idTeacher, date, idWeek);
    }

    public Groups findById(Long id) {
        return repositoryGroups.findById(id).orElseThrow();
    }

    public ResponseEntity saveNewGroup(GroupAddDTO group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups groups = repositoryGroups.findByName(group.getName()).orElse(null);
        if (groups != null) {
            return new ResponseEntity("Такая группа уже есть", HttpStatus.CONFLICT);
        } else {
            Curator curator = serviceCurator.findById(group.getIdCurator());
            if (curator == null) {
                return new ResponseEntity("Такого куратора нету", HttpStatus.BAD_REQUEST);
            }
            Specialization specialization = serviceSpecialization.findById(group.getIdSpecialization());
            if (specialization == null) {
                return new ResponseEntity("Такой специальности нету", HttpStatus.BAD_REQUEST);
            }

            groups = repositoryGroups.save(new Groups(group.getName(), LocalDate.now().getYear(), 1, specialization, curator));
            serviceCourses.saveNewCourses(groups);
            return new ResponseEntity(HttpStatus.OK);
        }
    }


    List<Groups> findByIdTeacher(Long id) {
        return repositoryGroups.findByIdTeacher(id);
    }

    Groups findByIdGroupAndIdSubject(Long id, Long idSubject) {
        return repositoryGroups.findByIdGroupAndIdSubject(id, idSubject);
    }

    public ResponseEntity findAll() {
        List<Groups> groups = repositoryGroups.findAll();
        List<GroupsDTO> groupsDTOS = new ArrayList<>();
        for (Groups g : groups) {
            groupsDTOS.add(new GroupsDTO(g.getId(), g.getName()));
        }
        return new ResponseEntity(groupsDTOS, HttpStatus.OK);
    }

    public ResponseEntity findAllInfo() {
        List<Groups> groups = repositoryGroups.findAll();
        List<GroupsInfoDTO> groupsDTOS = new ArrayList<>();
        for (Groups g : groups) {
            List<StudyProcessInfoDTO> semestersInfo = new ArrayList<>();
            List<StudyProcess> semesters = g.getStudyProcesses();
            for (StudyProcess studyProcess: semesters){
                List<TypeStudy> typeStudies = studyProcess.getTypeStudies();
                List<TypeStudyInfoDTO> typeStudyInfo = new ArrayList<>();
                for (TypeStudy typeStudy: typeStudies){
                    typeStudyInfo.add(new TypeStudyInfoDTO(typeStudy.getId(),typeStudy.getName(),typeStudy.getDateStart(),typeStudy.getDateEnd()));
                }
                semestersInfo.add(new StudyProcessInfoDTO(studyProcess.getId(),studyProcess.getSemester(),studyProcess.getCourse(),studyProcess.getDateStart(),studyProcess.getDateEnd(),typeStudyInfo));
            }
            groupsDTOS.add(new GroupsInfoDTO(g.getId(), g.getName(),semestersInfo));
        }
        return new ResponseEntity(groupsDTOS, HttpStatus.OK);
    }
}