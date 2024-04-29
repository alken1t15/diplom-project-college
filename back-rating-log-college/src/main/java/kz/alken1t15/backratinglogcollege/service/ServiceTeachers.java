package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceTeachers {
    private final RepositoryTeachers repositoryTeacher;
    private final ServiceHoweWork serviceHoweWork;
    private final ServiceFilesGroup serviceFilesGroup;
    private final ServiceUsers serviceUser;
    private final ServiceGroups serviceGroup;
    private final ServicePlanStudy servicePlanStudy;
    private final ServiceOmissions serviceOmissions;

    //TODO Проверка на уникальность
//    public ResponseEntity save(ControllerTeacher.Teacher teacher) {
//        if (StringUtils.isBlank(teacher.firstName()) || StringUtils.isBlank(teacher.secondName())
//                || StringUtils.isBlank(teacher.login()) || StringUtils.isBlank(teacher.password()) || StringUtils.isBlank(teacher.bornDate())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        else {
//            Teachers teachers = new Teachers(teacher.firstName(),teacher.secondName(),teacher.middleName(),teacher.login(),
//                    teacher.password(), LocalDate.parse(teacher.bornDate()));
//            repositoryTeachers.save(teachers);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        }
//    }


    public Teachers getTeachers(){
        User user = serviceUser.getUser();
        return repositoryTeacher.findById(user.getId()).orElseThrow();
    }

    //TODO Получение информации для главной страницы учителя
    public TeacherMainPageDTO getMainPageTeacher(Integer idGroupStep, Boolean certificateHave){
        Teachers teachers = getTeachers();
        List<Groups> groups = serviceGroup.findByAllGroupForTeacher(teachers.getId(), LocalDate.now());
        List<CurrentGraphStudyGroup> graphGroupsForStudy = getCurrentGraphStudyGroup(groups);
        List<CurrentOmissionStudent> currentOmissionStudents = getStudentsByGroup(graphGroupsForStudy,idGroupStep,certificateHave);

        return new TeacherMainPageDTO(graphGroupsForStudy,currentOmissionStudents,new TeacherDTOP(String.format("%s %s %s",teachers.getSecondName(),teachers.getFirstName(),teachers.getMiddleName()),teachers.getStartWork().getYear()));
    }


    public List<CurrentGraphStudyGroup> getCurrentGraphStudyGroup(List<Groups> groups){
        List<CurrentGraphStudyGroup> currentGraphStudyGroups = new ArrayList<>();
        for (Groups group: groups){
            String name = group.getName();
            List<PlanStudy> planStudies = servicePlanStudy.getPlanStudyToday(group.getId());
            for (PlanStudy p : planStudies){
                currentGraphStudyGroups.add(new CurrentGraphStudyGroup(name,p.getTimeStudy().getStartLesson(),p.getSubjectStudy().getName(),group.getId()));
            }
        }
        Collections.sort(currentGraphStudyGroups);
        return currentGraphStudyGroups;
    }

    public List<CurrentOmissionStudent> getStudentsByGroup(List<CurrentGraphStudyGroup> currentGraph,Integer id,Boolean certificateHave){
        List<CurrentOmissionStudent> currentOmissionStudents = new ArrayList<>();
        Long idGroup;
        String nameSubject;
        if (id==null){
            idGroup = currentGraph.get(0).idGroup;
            nameSubject = currentGraph.get(0).nameSubject;
        }
        else {
            idGroup = currentGraph.get(id-1).idGroup;
            nameSubject = currentGraph.get(id-1).nameSubject;
        }
        Groups group = serviceGroup.findById(idGroup);
        List<Students> students = group.getStudents();
        if (certificateHave){
            for (Students student: students) {
                Omissions omissions = serviceOmissions.findByDateAndSubjectName(LocalDate.now(), nameSubject, student.getId(), group.getCurrentCourse());
                if (omissions!= null){
                    currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s",student.getSecondName(),student.getFirstName(),student.getMiddleName()),0,omissions.getStatus(),omissions.getFilesStudent().getId()));
                }
            }
        }
        else {
            for (Students student: students){
                Omissions omissions = serviceOmissions.findByDateAndSubjectName(LocalDate.now(),nameSubject, student.getId(), group.getCurrentCourse());
                if (omissions==null){
                    currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s",student.getSecondName(),student.getFirstName(),student.getMiddleName()),0,null,null));
                }
                else {
                    currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s",student.getSecondName(),student.getFirstName(),student.getMiddleName()),0,omissions.getStatus(),null));
                }
            }
        }
        return currentOmissionStudents;
    }


    public record CurrentGraphStudyGroup(String name, LocalTime timeStart, String nameSubject,Long idGroup) implements Comparable<CurrentGraphStudyGroup>{
        @Override
        public int compareTo(CurrentGraphStudyGroup o) {
            return this.timeStart.compareTo(o.timeStart);
        }
    }


    public record CurrentOmissionStudent(String name,Integer count,String status, Long idCertificate){}

    public record TeacherDTOP(String name,Integer yearWork){}


}