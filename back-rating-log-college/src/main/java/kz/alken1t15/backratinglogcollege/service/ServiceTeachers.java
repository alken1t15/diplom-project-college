package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.CompleteHomeWorkDTO;
import kz.alken1t15.backratinglogcollege.dto.CompleteHomeWorkStudentDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceTeachers {
    private final RepositoryTeachers repositoryTeacher;
    private final ServiceUsers serviceUser;
    private final ServiceGroups serviceGroup;
    private final ServiceOmissions serviceOmissions;
    private final ServiceTaskStudents serviceTaskStudents;
    @Value("${path.save.file}")
    private String pathSaveFile;

    @Autowired
    public ServiceTeachers(RepositoryTeachers repositoryTeacher, ServiceUsers serviceUser, ServiceGroups serviceGroup, ServiceOmissions serviceOmissions, ServiceTaskStudents serviceTaskStudents) {
        this.repositoryTeacher = repositoryTeacher;
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceOmissions = serviceOmissions;
        this.serviceTaskStudents = serviceTaskStudents;
    }

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
        List<CompleteHomeWorkDTO> completeTask = getAllStudentCompleteTask();
        return new TeacherMainPageDTO(graphGroupsForStudy, currentOmissionStudents, new TeacherDTOP(String.format("%s %s %s", teachers.getSecondName(), teachers.getFirstName(), teachers.getMiddleName()), teachers.getStartWork().getYear()), currentOmissionStudents.size(), completeTask);
    }

    public List<CurrentOmissionStudent> getStudentsByGroup(List<CurrentGraphStudyGroup> currentGraph, Integer id, Boolean certificateHave) {
        List<CurrentOmissionStudent> currentOmissionStudents = new ArrayList<>();
        Long idGroup;
        String nameSubject;
        if (currentGraph.isEmpty()) {
            return new ArrayList<>();
        }
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

    //Получение учеников кто выполнил задание
    public List<CompleteHomeWorkDTO> getAllStudentCompleteTask() {
        Teachers teacher = getTeachers();
        List<TaskStudents> taskStudents = serviceTaskStudents.findByTeacherIdAndComplete(teacher.getId(), "Сдано");
        if (taskStudents.isEmpty()) {
            return new ArrayList<>();
        }
        List<CompleteHomeWorkDTO> homeWorkDTOS = new ArrayList<>();
        for (TaskStudents t : taskStudents) {
            HomeWork homeWork = t.getHoweWork();
            Students student = t.getStudent();
            String month = getMonthName(t.getTimeCompleted().getMonth().getValue());
            String complete = t.getTimeCompleted().getDayOfMonth() + " " + month;
            String monthDeadline = getMonthNameShort(homeWork.getEndDate().getMonth().getValue());
            String deadlineWork = homeWork.getEndDate().getDayOfMonth() + " " + monthDeadline;
            homeWorkDTOS.add(new CompleteHomeWorkDTO(homeWork.getId(), student.getId(), homeWork.getName(), student.getSecondName() + " " + student.getFirstName(), complete, deadlineWork));
        }
        return homeWorkDTOS;
    }

    //Получение конкретного ученика и его работы
    public ResponseEntity getHomeWorkStudent(Long idWork, Long idStudent, BindingResult bindingResult) {
        if (idWork == null || idStudent == null){
            CompleteHomeWorkStudentDTO completeHomeWork = new CompleteHomeWorkStudentDTO();
            List<CompleteHomeWorkDTO> completeHomeWorkDTOS = getAllStudentCompleteTask();
            completeHomeWork.setCompleteHomeWork(completeHomeWorkDTOS);
            return new ResponseEntity(completeHomeWork,HttpStatus.OK);
        }
        TaskStudents taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(idWork, idStudent, "Сдано");
        HomeWork homeWork = taskStudents.getHoweWork();
        Students student = taskStudents.getStudent();
        Teachers teacher = homeWork.getTeacher();
        String monthStart = getMonthNameShort(homeWork.getStartDate().getMonth().getValue());
        String allStart = homeWork.getStartDate().getDayOfMonth() + " " + monthStart;
        String monthEnd = getMonthNameShort(homeWork.getEndDate().getMonth().getValue());
        String allEnd = homeWork.getEndDate().getDayOfMonth() + " " + monthEnd;
        String nameTeacher;
        if (!StringUtils.isBlank(teacher.getMiddleName())) {
            nameTeacher = teacher.getSecondName() + " " + teacher.getFirstName() + " " + teacher.getMiddleName();
        } else {
            nameTeacher = teacher.getSecondName() + " " + teacher.getFirstName();
        }
        String nameStudent;
        if (!StringUtils.isBlank(teacher.getMiddleName())) {
            nameStudent = student.getSecondName() + " " + student.getFirstName() + " " + student.getMiddleName();
        } else {
            nameStudent = student.getSecondName() + " " + student.getFirstName();
        }
        String month = getMonthName(taskStudents.getTimeCompleted().getMonth().getValue());
        String completeDate = taskStudents.getTimeCompleted().getDayOfMonth() + " " + month;
        List<FileHomeTask> homeTasks = homeWork.getFileHomeTasks();
        List<byte[]> fileWork = new ArrayList<>();
        if (!homeTasks.isEmpty()) {
            for (FileHomeTask t : homeTasks) {
                fileWork.add(readFile(t.getName()));
            }
        }
        List<TaskStudentsFiles> taskStudentsFiles = taskStudents.getTaskStudentsFiles();
        List<byte[]> fileStudent = new ArrayList<>();
        if (!taskStudentsFiles.isEmpty()) {
            for (TaskStudentsFiles t : taskStudentsFiles) {
                fileStudent.add(readFile(t.getFilesStudent().getName()));
            }
        }
        List<CompleteHomeWorkDTO> completeHomeWorkDTOS = getAllStudentCompleteTask();
        return new ResponseEntity<>(new CompleteHomeWorkStudentDTO(homeWork.getName(), allStart, allEnd, nameTeacher, homeWork.getNameSubject(), homeWork.getDescription(), nameStudent, completeDate, fileWork, fileStudent, completeHomeWorkDTOS), HttpStatus.OK);
    }


    public byte[] readFile(String name) {
        byte[] fileContent = new byte[0];
        try {
            return fileContent = Files.readAllBytes(Paths.get(pathSaveFile + name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMonthName(int idMonth) {
        idMonth -= 1;
        String[] month = new String[]{"января", "февраля",
                "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        return month[idMonth];
    }

    public String getMonthNameShort(int idMonth) {
        idMonth -= 1;
        String[] month = new String[]{"янв", "фев",
                "март", "апр", "май", "июнь", "июль", "авг", "сен", "окт", "ноя", "дек"};
        return month[idMonth];
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
        if (user != null) {
            return new ResponseEntity("Такой логи занят", HttpStatus.CONFLICT);
        }
        Long idUser = serviceUser.save(teacher.getLogin(), teacher.getPassword(), "teacher");
        if (StringUtils.isBlank(teacher.getMiddleName())) {
            save(new Teachers(idUser, teacher.getFirstName(), teacher.getSecondName(), teacher.getBornDate(), teacher.getStartWork()));
        } else {
            save(new Teachers(idUser, teacher.getFirstName(), teacher.getSecondName(), teacher.getMiddleName(), teacher.getBornDate(), teacher.getStartWork()));
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    public record CurrentOmissionStudent(String name, Integer count, String status, Long idCertificate,
                                         Long idStudent) {
    }

    public record TeacherDTOP(String name, Integer yearWork) {
    }


}