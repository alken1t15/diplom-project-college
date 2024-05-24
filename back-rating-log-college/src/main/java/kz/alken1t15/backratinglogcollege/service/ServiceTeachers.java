package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
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
public class ServiceTeachers {
    private final RepositoryTeachers repositoryTeacher;
    private final ServiceUsers serviceUser;
    private final ServiceGroups serviceGroup;
    private final ServiceOmissions serviceOmissions;


    //Получение учителя
    public Teachers getTeachers() {
        User user = serviceUser.getUser();
        return repositoryTeacher.findById(user.getId()).orElseThrow();
    }

    //Получение информации для главной страницы учителя
    public TeacherMainPageDTO getMainPageTeacher(Integer idGroupStep, Boolean certificateHave) {
        Teachers teachers = getTeachers();
        List<CurrentGraphStudyGroup> graphGroupsForStudy = serviceGroup.findByAllGroupForTeacher(teachers.getId(), LocalDate.now(), (long) LocalDate.now().getDayOfWeek().getValue());
        List<CurrentOmissionStudent> currentOmissionStudents = getStudentsByGroup(graphGroupsForStudy, idGroupStep, certificateHave);
        return new TeacherMainPageDTO(graphGroupsForStudy, currentOmissionStudents, new TeacherDTOP(String.format("%s %s %s", teachers.getSecondName(), teachers.getFirstName(), teachers.getMiddleName()), teachers.getStartWork().getYear()),currentOmissionStudents.size());
    }

    public List<CurrentOmissionStudent> getStudentsByGroup(List<CurrentGraphStudyGroup> currentGraph, Integer id, Boolean certificateHave) {
        List<CurrentOmissionStudent> currentOmissionStudents = new ArrayList<>();
        Long idGroup;
        String nameSubject;
        if (certificateHave == null) {
            certificateHave = false;
        }
        if (id == null) {
            idGroup = currentGraph.get(0).getIdGroup();
            nameSubject = currentGraph.get(0).getNameSubject();
        } else {
            idGroup = currentGraph.get(id).getIdGroup();
            nameSubject = currentGraph.get(id).getNameSubject();
        }
        Groups group = serviceGroup.findById(idGroup);
        List<Students> students = group.getStudents();
        if (certificateHave) {
            for (Students student : students) {
                Omissions omissions = serviceOmissions.findByDateAndSubjectNameAndCertificate(LocalDate.now(), nameSubject, student.getId(), group.getCurrentCourse());
                if (omissions != null) {
                    Integer count = serviceOmissions.findBySubjectNameAndIdStudentCountOmission(nameSubject, student.getId(), group.getCurrentCourse());
                    currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s", student.getSecondName(), student.getFirstName(), student.getMiddleName()), count, omissions.getStatus(), omissions.getFilesStudent().getId(), student.getId()));
                }
            }
        } else {
            for (Students student : students) {
                Omissions omissions = serviceOmissions.findByDateAndSubjectName(LocalDate.now(), nameSubject, student.getId(), group.getCurrentCourse());
                Omissions omissionsHaseCertificate = serviceOmissions.findByDateAndSubjectNameAndCertificate(LocalDate.now(), nameSubject, student.getId(), group.getCurrentCourse());
                Integer count = serviceOmissions.findBySubjectNameAndIdStudentCountOmission(nameSubject, student.getId(), group.getCurrentCourse());
                if (omissionsHaseCertificate == null) {
                    if (omissions == null) {
                        currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s", student.getSecondName(), student.getFirstName(), student.getMiddleName()), count, null, null, student.getId()));
                    } else {
                        currentOmissionStudents.add(new CurrentOmissionStudent(String.format("%s %s %s", student.getSecondName(), student.getFirstName(), student.getMiddleName()), count, omissions.getStatus(), null, student.getId()));
                    }
                }
            }
        }
        return currentOmissionStudents;
    }

    //Обновление данных пользователя
    public void save(Teachers teacher) {
        repositoryTeacher.save(teacher);
    }
    //Сохранение нового учителя
    public ResponseEntity saveNewTeacher(TeacherAddDTO teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        User user = serviceUser.getUser(teacher.getLogin());
        if (user!=null){
            return new ResponseEntity("Такой логи занят",HttpStatus.CONFLICT);
        }
       Long idUser = serviceUser.save(teacher.getLogin(),teacher.getPassword(),"teacher");
        if (StringUtils.isBlank(teacher.getMiddleName())){
            save(new Teachers(idUser, teacher.getFirstName(),teacher.getSecondName(),teacher.getBornDate(),teacher.getStartWork()));
        }else {
            save(new Teachers(idUser, teacher.getFirstName(), teacher.getSecondName(), teacher.getMiddleName(), teacher.getBornDate(), teacher.getStartWork()));
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    public record CurrentOmissionStudent(String name, Integer count, String status, Long idCertificate,Long idStudent) {
    }

    public record TeacherDTOP(String name, Integer yearWork) {
    }


}