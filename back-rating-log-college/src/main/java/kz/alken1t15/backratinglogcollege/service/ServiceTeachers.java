package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryEvaluations;
import kz.alken1t15.backratinglogcollege.repository.RepositorySubject;
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

    private final ServiceGroups serviceGroups;

    private final RepositorySubject repositorySubject;

    private final ServiceStudents serviceStudents;

    private final RepositoryEvaluations repositoryEvaluations;

    @Value("${path.save.file}")
    private String pathSaveFile;

    @Autowired
    public ServiceTeachers(RepositoryTeachers repositoryTeacher, ServiceUsers serviceUser, ServiceGroups serviceGroup, ServiceOmissions serviceOmissions, ServiceTaskStudents serviceTaskStudents, ServiceGroups serviceGroups, RepositorySubject repositorySubject, ServiceStudents serviceStudents, RepositoryEvaluations repositoryEvaluations) {
        this.repositoryTeacher = repositoryTeacher;
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceOmissions = serviceOmissions;
        this.serviceTaskStudents = serviceTaskStudents;
        this.serviceGroups = serviceGroups;
        this.repositorySubject = repositorySubject;
        this.serviceStudents = serviceStudents;
        this.repositoryEvaluations = repositoryEvaluations;
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
    public ResponseEntity getHomeWork(List<HomeWork> homeWorks) {
        if (homeWorks.isEmpty()) {
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        }
        List<HomeWorkTeacherDTO> homeWorkTeacherDTOs = new ArrayList<>();
        for (HomeWork h : homeWorks) {
            String monthEnd = getMonthName(h.getEndDate().getMonth().getValue());
            String allEnd = h.getEndDate().getDayOfMonth() + " " + monthEnd;
            Integer countGroupPeople = h.getGroup().getStudents().size();
            List<Students> students = h.getGroup().getStudents();
            Integer count = 0;
            for (Students s : students) {
                TaskStudents taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Сдано");
                if (taskStudents != null) {
                    count++;
                }
                taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Проверено");
                if (taskStudents != null) {
                    count++;
                }

            }
            String countTotal = count + "/" + countGroupPeople;
            homeWorkTeacherDTOs.add(new HomeWorkTeacherDTO(h.getId(), h.getName(), h.getNameSubject(), h.getGroup().getName(), allEnd, countTotal));
        }
        return new ResponseEntity(homeWorkTeacherDTOs, HttpStatus.OK);
    }

    public ResponseEntity getHomeWorkId(HomeWork h, String name, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        String monthEnd = getMonthName(h.getEndDate().getMonth().getValue());
        String allEnd = h.getEndDate().getDayOfMonth() + " " + monthEnd;
        Integer countGroupPeople = h.getGroup().getStudents().size();
        List<Students> students = h.getGroup().getStudents();
        Integer count = 0;
        List<StudentHomeWorkDTO> homeWorkGetDTOs = new ArrayList<>();
        for (Students s : students) {
            TaskStudents taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Сдано");
            if (taskStudents != null) {
                count++;
            }
            taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Проверено");
            if (taskStudents != null) {
                count++;
            }
            if (name.equals("Сдано")) {
                taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Сдано");
                if (taskStudents != null) {
                    String nameStudent = s.getSecondName() + " " + s.getFirstName();
                    List<byte[]> files = new ArrayList<>();
                    List<TaskStudentsFiles> taskStudentsFiles = taskStudents.getTaskStudentsFiles();
                    for (TaskStudentsFiles t : taskStudentsFiles) {
                        files.add(readFile(t.getFilesStudent().getName()));
                    }
                    homeWorkGetDTOs.add(new StudentHomeWorkDTO(s.getId(), nameStudent, null, files));
                }
            } else if (name.equals("Проверено")) {
                taskStudents = serviceTaskStudents.findByWorkIdAndStudentIdAndComplete(h.getId(), s.getId(), "Проверено");
                if (taskStudents != null) {
                    String nameStudent = s.getSecondName() + " " + s.getFirstName();
                    String ball = String.valueOf(taskStudents.getBall());
                    List<byte[]> files = new ArrayList<>();
                    List<TaskStudentsFiles> taskStudentsFiles = taskStudents.getTaskStudentsFiles();
                    for (TaskStudentsFiles t : taskStudentsFiles) {
                        files.add(readFile(t.getFilesStudent().getName()));
                    }
                    homeWorkGetDTOs.add(new StudentHomeWorkDTO(s.getId(), nameStudent, ball, files));
                }

            }
        }
        String countTotal = count + "/" + countGroupPeople;
        HomeWorkStudentDTO homeWorkTeacherDTO = new HomeWorkStudentDTO(h.getId(), h.getName(), h.getNameSubject(), h.getGroup().getName(), allEnd, countTotal, homeWorkGetDTOs);
        return new ResponseEntity(homeWorkTeacherDTO, HttpStatus.OK);
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

    //Получение учителя по id
    public Teachers findById(Long id) {
        return repositoryTeacher.findById(id).orElse(null);
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

    public ResponseEntity getStudy() {
        Teachers teachers = getTeachers();
        List<GroupsStudyDTO> groupsStudyDTOS = new ArrayList<>();
        List<SubjectStudyDTO> subjectStudyDTOS = new ArrayList<>();
        List<Groups> groups = serviceGroup.findByIdTeacher(teachers.getId());
        if (!groups.isEmpty()) {
            for (Groups g : groups) {
                groupsStudyDTOS.add(new GroupsStudyDTO(g.getId(), g.getName()));
            }
        }
        List<SubjectStudy> subjectStudies = repositorySubject.findByIdTeacher(teachers.getId());
        if (!subjectStudies.isEmpty()) {
            for (SubjectStudy s : subjectStudies) {
                subjectStudyDTOS.add(new SubjectStudyDTO(s.getId(), s.getName()));
            }
        }
        return new ResponseEntity(new StudyDTO(groupsStudyDTOS, subjectStudyDTOS), HttpStatus.OK);
    }

    public ResponseEntity getStudent(StudyFindDTO studyFindDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups group = serviceGroup.findByIdGroupAndIdSubject(studyFindDTO.getIdGroup(), studyFindDTO.getIdSubject());
        List<Students> students = group.getStudents();
        List<StudentsStudyDTO> studentsStudyDTOS = new ArrayList<>();
        for (Students s : students) {
            String name = s.getSecondName() + " " + s.getFirstName();
            Integer countOmissions = serviceOmissions.getOmissionsStudent(s.getId());
            Integer count = 0;
            count += countOmissions;
            studentsStudyDTOS.add(new StudentsStudyDTO(s.getId(), name, String.valueOf(count)));
        }
        return new ResponseEntity(new GroupStudyDTOS(studentsStudyDTOS, students.size()), HttpStatus.OK);
    }

    public ResponseEntity addBullStudent(AddBullStudentDTO addBullStudentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Teachers teacher = getTeachers();
        String name = teacher.getSecondName() + " " + teacher.getFirstName();
        SubjectStudy subjectStudy = repositorySubject.findById(addBullStudentDTO.getIdSubject()).orElseThrow();
        for (BullStudentDTO b : addBullStudentDTO.getStudents()) {
            Students students = serviceStudents.findByIdStudent(b.getId());
            List<StudentsCourse> studentsCourses = students.getStudentsCourses();
            StudentsCourse studentsCourse = null;
            for (StudentsCourse s : studentsCourses) {
                if (studentsCourse == null) {
                    studentsCourse = s;
                } else if (studentsCourse.getCourse() < s.getCourse()) {
                    studentsCourse = s;
                }
                long bull = b.getBull();
                repositoryEvaluations.save(new Evaluations(studentsCourse, subjectStudy.getName(), addBullStudentDTO.getDate(), bull, name));
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    public record CurrentOmissionStudent(String name, Integer count, String status, Long idCertificate,
                                         Long idStudent) {
    }

    public record TeacherDTOP(String name, Integer yearWork) {
    }


}