package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.work.MonthReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceStudyProcess {
    private RepositoryStudyProcess repositoryStudyProcess;
    private RepositoryEvaluations repositoryEvaluation;
    private RepositoryCourses repositoryCourses;
    private ServiceStudents serviceStudents;


    public ProcessReturnDTO getStudyProcess(ProcessDTO process) {
        Students student = serviceStudents.getStudent();
        StudyProcess studyProcess;
        if (process.getSemester() != null && process.getCourse() != null) {
            studyProcess = repositoryStudyProcess.findByCourseSemesterGroup(process.getCourse(), process.getSemester(), student.getGroup().getId());
        } else {
            studyProcess = repositoryStudyProcess.findByCourseSemesterGroup(student.getGroup().getCurrentCourse(), 1, student.getGroup().getId());
        }
        ProcessReturnDTO processReturnDTO = new ProcessReturnDTO();

        if (process.getSemester() != null && process.getCourse() != null) {
            processReturnDTO.setCurrentCourse(process.getCourse());
            processReturnDTO.setCurrentSemester(process.getSemester());
        } else {
            processReturnDTO.setCurrentCourse(student.getGroup().getCurrentCourse());
            processReturnDTO.setCurrentSemester(1);
        }
        processReturnDTO.setTotalCourse(student.getGroup().getCourses().size());

        List<MonthReturnDTO> months = getAllMonths(studyProcess.getDateStart(), studyProcess.getDateEnd());

        processReturnDTO.setMonths(months);
        List<Evaluations> evaluations;
        List<String> nameObjects;
        List<Evaluations> evaluationsObjects;
        LocalDate yearStart;
        int daysInMonth;
        List<String[]> evalStudy = new ArrayList<>();

        if (process.getMonth() != null && process.getCourse() != null) {
            Courses courses = repositoryCourses.findByCourseGroup(process.getCourse(), student.getGroup().getId());
            YearMonth yearMonth;
            LocalDate yearEnd;
            if (process.getMonth() >= 9) {
                yearStart = LocalDate.of(courses.getYear(), process.getMonth(), 1);
                yearMonth = YearMonth.of(courses.getYear(), process.getMonth());
                daysInMonth = yearMonth.lengthOfMonth();
                yearEnd = LocalDate.of(courses.getYear(), process.getMonth(), daysInMonth);
            } else {
                yearStart = LocalDate.of(courses.getYear() + 1, process.getMonth(), 1);
                yearMonth = YearMonth.of(courses.getYear() + 1, process.getMonth());
                daysInMonth = yearMonth.lengthOfMonth();
                yearEnd = LocalDate.of(courses.getYear() + 1, process.getMonth(), daysInMonth);
            }
            nameObjects = repositoryEvaluation.findByDateStudentCourseDistinctName(yearStart, yearEnd, process.getCourse(), student.getId());
            for (String name : nameObjects) {
                evaluationsObjects = repositoryEvaluation.findByDateStudentCourseNameObject(yearStart, yearEnd, process.getCourse(), student.getId(), name);
                String[] tempArr = new String[daysInMonth + 1];
                tempArr[0] = name;
                for (Evaluations e : evaluationsObjects) {
                    LocalDate localDate = e.getDateEvaluation();
                    tempArr[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                }
                evalStudy.add(tempArr);
            }
        } else {
            Courses courses = repositoryCourses.findByCourseGroup(student.getGroup().getCurrentCourse(), student.getGroup().getId());
            YearMonth yearMonth;
            LocalDate yearEnd;
            int monthOfNumber = months.get(0).getRequestMonth();
            if (monthOfNumber >= 9) {
                yearStart = LocalDate.of(courses.getYear(), monthOfNumber, 1);
                yearMonth = YearMonth.of(courses.getYear(), monthOfNumber);
                daysInMonth = yearMonth.lengthOfMonth();
                yearEnd = LocalDate.of(courses.getYear(), monthOfNumber, daysInMonth);
            } else {
                yearStart = LocalDate.of(courses.getYear() + 1, monthOfNumber, 1);
                yearMonth = YearMonth.of(courses.getYear() + 1, monthOfNumber);
                daysInMonth = yearMonth.lengthOfMonth();
                yearEnd = LocalDate.of(courses.getYear() + 1, monthOfNumber, daysInMonth);
            }
            nameObjects = repositoryEvaluation.findByDateStudentCourseDistinctName(yearStart, yearEnd, student.getGroup().getCurrentCourse(), student.getId());
            for (String name : nameObjects) {
                evaluationsObjects = repositoryEvaluation.findByDateStudentCourseNameObject(yearStart, yearEnd, student.getGroup().getCurrentCourse(), student.getId(), name);
                String[] tempArr = new String[daysInMonth + 1];
                tempArr[0] = name;
                for (Evaluations e : evaluationsObjects) {
                    LocalDate localDate = e.getDateEvaluation();
                    tempArr[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                }
                evalStudy.add(tempArr);
            }
        }
        List<String> arr = new ArrayList<>();
        String[] weeks = new String[]{"Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};
        int year = yearStart.getYear();
        int month = yearStart.getMonthValue();
        arr.add("Предметы\\Даты");
        for (int i = 1; i <= daysInMonth; i++) {
            yearStart = LocalDate.of(year, month, i);
            int temp = yearStart.getDayOfWeek().getValue();
            if (temp == 7) {
                continue;
            }
            arr.add(yearStart.getDayOfMonth() + weeks[temp - 1]);
        }
        arr.add("Итог");
        String[] stringArray = arr.toArray(new String[0]);
        evalStudy.add(0, stringArray);

        processReturnDTO.setEvaluations(evalStudy);

        return processReturnDTO;
    }


    private static List<MonthReturnDTO> getAllMonths(LocalDate startDate, LocalDate endDate) {
        String[] arrMonth = new String[]{"01 Янв", "02 Фев", "03 Март", "04 Апр", "05 Май", "06 Июн", "", "", "09 Сен", "10 Окт", "11 Нояб", "12 Дек"};

        List<MonthReturnDTO> months = new ArrayList<>();
        LocalDate currentMonth = startDate.withDayOfMonth(1);

        while (!currentMonth.isAfter(endDate.withDayOfMonth(1))) {
            months.add(new MonthReturnDTO(arrMonth[currentMonth.getMonth().getValue() - 1], currentMonth.getMonth().getValue()));
            currentMonth = currentMonth.plusMonths(1);
        }

        return months;
    }
}