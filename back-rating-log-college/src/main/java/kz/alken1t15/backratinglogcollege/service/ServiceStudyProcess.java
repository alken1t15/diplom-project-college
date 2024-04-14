package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.work.MonthReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.PlanStudyFindDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
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
    private RepositoryTypeStudy repositoryTypeStudy;
    private RepositoryPlanStudy repositoryPlanStudy;


    public ProcessReturnDTO getStudyProcess(ProcessDTO process) {
        ProcessReturnDTO processReturnDTO = new ProcessReturnDTO();
        Students student = serviceStudents.getStudent();
        StudyProcess studyProcess;
        List<Long> typeStudiesId;
        if (process.getSemester() != null && process.getCourse() != null) {
            studyProcess = getStudyProcessForSemester(process.getCourse(), process.getSemester(), student.getGroup().getId());
            processReturnDTO.setCurrentCourse(process.getCourse());
            processReturnDTO.setCurrentSemester(process.getSemester());
            typeStudiesId = repositoryTypeStudy.findByIdStudyProcess(studyProcess.getId());
        } else {
            studyProcess = getStudyProcessForSemester(student.getGroup().getCurrentCourse(), 1, student.getGroup().getId());
            processReturnDTO.setCurrentCourse(student.getGroup().getCurrentCourse());
            processReturnDTO.setCurrentSemester(1);
            typeStudiesId = repositoryTypeStudy.findByIdStudyProcess(studyProcess.getId());
        }
        List<PlanStudyFindDTO> teachers = new ArrayList<>();
        for (Long id : typeStudiesId){
            List<PlanStudyFindDTO> temp = repositoryPlanStudy.findByIdTypeStudy(id);
            teachers.addAll(temp);
        }
        processReturnDTO.setTeachers(teachers);
        processReturnDTO.setTotalCourse(student.getGroup().getCourses().size());
        List<MonthReturnDTO> months = getAllMonths(studyProcess.getDateStart(), studyProcess.getDateEnd());
        processReturnDTO.setMonths(months);
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
                long total = 0;
                int count =0;
                for (Evaluations e : evaluationsObjects) {
                    total = e.getBall() + total;
                    LocalDate localDate = e.getDateEvaluation();
                    tempArr[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                    count++;
                }
                tempArr[tempArr.length-1] = String.valueOf(total/count);
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
                int count =0;
                long total = 0;
                for (Evaluations e : evaluationsObjects) {
                    LocalDate localDate = e.getDateEvaluation();
                    total = e.getBall() + total;
                    tempArr[localDate.getDayOfMonth()] = String.valueOf(e.getBall());
                    count++;
                }
                tempArr[tempArr.length-1] = String.valueOf(total/count);
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


    public  List<MonthReturnDTO> getAllMonths(LocalDate startDate, LocalDate endDate) {
        String[] arrMonth = new String[]{"01 Янв", "02 Фев", "03 Март", "04 Апр", "05 Май", "06 Июн", "", "", "09 Сен", "10 Окт", "11 Нояб", "12 Дек"};

        List<MonthReturnDTO> months = new ArrayList<>();
        LocalDate currentMonth = startDate.withDayOfMonth(1);

        while (!currentMonth.isAfter(endDate.withDayOfMonth(1))) {
            months.add(new MonthReturnDTO(arrMonth[currentMonth.getMonth().getValue() - 1], currentMonth.getMonth().getValue()));
            currentMonth = currentMonth.plusMonths(1);
        }

        return months;
    }

    public StudyProcess getStudyProcessForSemester(Integer course,Integer semester,Long idGroup){
     return  repositoryStudyProcess.findByCourseSemesterGroup(course, semester, idGroup);
    }

    public List<MonthReturnDTO> getStudyProcessAll(Integer course,Long idGroup){
        List<StudyProcess> processes = repositoryStudyProcess.findByCourseGroup(course,  idGroup);
        StudyProcess studyProcess = processes.get(0);
        StudyProcess studyProcess2 = processes.get(1);
        List<MonthReturnDTO> arr1 = getAllMonths(studyProcess.getDateStart(),studyProcess.getDateEnd());
        List<MonthReturnDTO> arr2 = getAllMonths(studyProcess2.getDateStart(),studyProcess2.getDateEnd());
        arr1.addAll(arr2);
        return arr1;
    }

}