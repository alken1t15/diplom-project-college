package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.entity.User;
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
    public Object getMainPageTeacher(){
        Teachers teachers = getTeachers();
        List<Groups> groups = serviceGroup.findByAllGroupForTeacher(teachers.getId(), LocalDate.now());
        List<CurrentGraphStudyGroup> graphGroupsForStudy = getCurrentGraphStudyGroup(groups);

        return null;
    }


    public List<CurrentGraphStudyGroup> getCurrentGraphStudyGroup(List<Groups> groups){
        List<CurrentGraphStudyGroup> currentGraphStudyGroups = new ArrayList<>();
        for (Groups group: groups){
            String name = group.getName();
            List<PlanStudy> planStudies = servicePlanStudy.getPlanStudyToday(group.getId());
            for (PlanStudy p : planStudies){
                currentGraphStudyGroups.add(new CurrentGraphStudyGroup(name,p.getTimeStudy().getStartLesson(),p.getSubjectStudy().getName()));
            }
        }
        Collections.sort(currentGraphStudyGroups);
        return currentGraphStudyGroups;
    }

//    public Object getStudentsByGroup(Groups group,String nameObject){
//        List<Students> students = group.getStudents();
//        for (Students student: students){
//
//        }
//    }


    public record CurrentGraphStudyGroup(String name, LocalTime timeStart, String nameSubject) implements Comparable<CurrentGraphStudyGroup>{
        @Override
        public int compareTo(CurrentGraphStudyGroup o) {
            return this.timeStart.compareTo(o.timeStart);
        }
    }

}