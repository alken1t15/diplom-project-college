package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.StudentGroupDTO;
import kz.alken1t15.backratinglogcollege.dto.StudentGroupTotalDTO;
import kz.alken1t15.backratinglogcollege.dto.TeacherGroupStudent;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceEvaluations {
    private final RepositoryEvaluations repositoryEvaluations;
    private final RepositoryStudents repositoryStudents;
    private final RepositoryGroups repositoryGroups;
    private final RepositorySubject repositorySubject;
    private final RepositoryCourses repositoryCourses;
    private final RepositoryTaskStudents repositoryTaskStudents;
    private final RepositoryStudyProcess repositoryStudyProcess;


    public List<Evaluations> findByDateEvaluation(LocalDate date, Long idStudent) {
        return repositoryEvaluations.findByDateEvaluation(date, idStudent);
    }

    public ResponseEntity getEvaluationStudentGroup(StudentGroupDTO studentGroupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups group = repositoryGroups.findById(studentGroupDTO.getId()).orElseThrow();
        SubjectStudy subjectStudy = repositorySubject.findById(studentGroupDTO.getSubject()).orElseThrow();
        List<Students> students = group.getStudents();
        Courses course = repositoryCourses.findByCourseGroup(studentGroupDTO.getCourse(), group.getId());
        List<String[]> evalStudy = new ArrayList<>();
        int year = 0;
        if (studentGroupDTO.getMonth() <= 6) {
            year = course.getYear() + 1;
        } else {
            year = course.getYear();
        }
        LocalDate startDate = LocalDate.of(year, studentGroupDTO.getMonth(), 1);
        LocalDate endDate = LocalDate.of(year, studentGroupDTO.getMonth(), startDate.lengthOfMonth());
        for (Students student : students) {
            String[] array = new String[startDate.lengthOfMonth() + 1];
            array[0] = student.getSecondName() + " " + student.getFirstName();
            List<Evaluations> evaluations = repositoryEvaluations.findByDateStudentCourseNameObject(startDate, endDate, studentGroupDTO.getCourse(), student.getId(), subjectStudy.getName());
            List<TaskStudents> taskStudents = repositoryTaskStudents.findByIdStudentAndDate(student.getId(), startDate, endDate, subjectStudy.getName());
            ;
            long total = 0;
            int count = 0;
            for (Evaluations e : evaluations) {
                total = e.getBall() + total;
                LocalDate localDate = e.getDateEvaluation();
                array[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                count++;
            }
            for (TaskStudents t : taskStudents) {
                total = t.getBall() + total;
                LocalDate localDate = t.getDateBall();
                array[localDate.getDayOfMonth()] = String.valueOf(t.getBall());
                count++;
            }
            ArrayList<String> arrayList = new ArrayList<>();
            LocalDate day = startDate;
            for (int i = 1; i < array.length; i++) {
                String str = array[i];
                int dayOfWeek = day.getDayOfWeek().getValue();
                if (dayOfWeek == 7) {
                    day = day.plusDays(1);
                    continue;
                }
                arrayList.add(str);
                day = day.plusDays(1);
            }
            if (count == 0) {
                arrayList.add("0");
            } else {
                arrayList.add(String.valueOf(total / count));
            }
            evalStudy.add(arrayList.toArray(new String[0]));
        }
        String[] weeks = new String[]{"Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};
        ArrayList<String> days = new ArrayList<>();
        days.add("ФИО/Студента");
        for (int i = 1; i <= startDate.lengthOfMonth(); i++) {
            LocalDate day = LocalDate.of(year, studentGroupDTO.getMonth(), i);
            int dayName = day.getDayOfWeek().getValue() - 1;
            if (dayName == 6) {
                continue;
            }
            days.add(i + " " + weeks[dayName]);
        }
        return new ResponseEntity(new TeacherGroupStudent(days, evalStudy), HttpStatus.OK);
    }

    public ResponseEntity getEvaluationStudentGroupTotal(StudentGroupTotalDTO studentGroupTotalDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups group = repositoryGroups.findById(studentGroupTotalDTO.getId()).orElseThrow();
        SubjectStudy subjectStudy = repositorySubject.findById(studentGroupTotalDTO.getSubject()).orElseThrow();
        List<Students> students = group.getStudents();
        StudyProcess studyProcess = repositoryStudyProcess.findByCourseSemesterGroup(studentGroupTotalDTO.getCourse(), studentGroupTotalDTO.getSemester(), group.getId());
        ArrayList<String[]> totals = new ArrayList<>();
        if (studyProcess == null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        for (Students student : students) {
            String[] array = new String[studyProcess.getDateStart().lengthOfMonth() + 1];
            array[0] = student.getSecondName() + " " + student.getFirstName();
            List<Evaluations> evaluations = repositoryEvaluations.findByDateStudentCourseNameObject(studyProcess.getDateStart(), studyProcess.getDateEnd(), studentGroupTotalDTO.getCourse(), student.getId(), subjectStudy.getName());
            List<TaskStudents> taskStudents = repositoryTaskStudents.findByIdStudentAndDate(student.getId(), studyProcess.getDateStart(), studyProcess.getDateEnd(), subjectStudy.getName());
            long total = 0;
            int count = 0;
            for (Evaluations e : evaluations) {
                total = e.getBall() + total;
                LocalDate localDate = e.getDateEvaluation();
                array[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                count++;
            }
            for (TaskStudents t : taskStudents) {
                total = t.getBall() + total;
                LocalDate localDate = t.getDateBall();
                array[localDate.getDayOfMonth()] = String.valueOf(t.getBall());
                count++;
            }

            String[] arr = new String[2];
            arr[0] = student.getSecondName() + " " + student.getFirstName();
            if (count == 0) {
                arr[1] = "0";
            } else {
                arr[1] = String.valueOf(total / count);
            }
            totals.add(arr);
        }

        return new ResponseEntity(totals, HttpStatus.OK);
    }
}