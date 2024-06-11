package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.dto.DayDTO;
import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.TardinessDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryOmissions;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudentCourse;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service

public class ServiceOmissions {
    @Autowired
    private RepositoryOmissions repositoryOmissions;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryGroups repositoryGroups;
    @Autowired
    private RepositoryStudentCourse repositoryStudentCourse;
    private final String[] russianDayOfWeekNames = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    private final String[] russianMonthNames = {
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
    };

    private final String[] russianMonth = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    public MonthDTO findByMonth(Integer numberMonth, Integer course, Long idStudent) {
        List<DayDTO> dayDTOs = new ArrayList<>();
        List<Omissions> omissions = repositoryOmissions.findByMonth(numberMonth, course, idStudent);

        for (Omissions o : omissions) {
            LocalDate localDate = o.getDateOmissions();

            int dayOfWeekIndex = localDate.getDayOfWeek().getValue() - 1;
            String dayOfWeekName = russianDayOfWeekNames[dayOfWeekIndex];

            Month month = localDate.getMonth();
            String monthName = russianMonthNames[month.getValue() - 1];

            String date = localDate.getDayOfMonth() + " " + monthName;
            DayDTO dayDTO = new DayDTO();
            dayDTO.setDate(date);
            dayDTO.setNameOfDay(dayOfWeekName);
            dayDTO.setTardiness(new ArrayList<>());
            if (!dayDTOs.contains(dayDTO)) {
                dayDTOs.add(dayDTO);
            }
        }

        for (Omissions o : omissions) {
            LocalDate localDate = o.getDateOmissions();
            Month month = localDate.getMonth();
            String monthName = russianMonthNames[month.getValue() - 1];
            String date = localDate.getDayOfMonth() + " " + monthName;
            for (DayDTO d : dayDTOs) {
                if (d.getDate().equals(date)) {
                    d.getTardiness().add(new TardinessDTO(o.getStatus(), o.getNameObject()));
                }
            }
        }
        return new MonthDTO(russianMonth[numberMonth - 1], dayDTOs);
    }

    public Omissions findByDateAndSubjectName(LocalDate date, String nameSubject, Long idStudent, Integer course) {
        return repositoryOmissions.findByDateAndSubjectName(date, nameSubject, idStudent, course);
    }

    public Omissions findByDateAndSubjectNameAndCertificate(LocalDate date, String nameSubject, Long idStudent, Integer course) {
        return repositoryOmissions.findByDateAndSubjectNameAndCertificate(date, nameSubject, idStudent, course);
    }

    public Integer findBySubjectNameAndIdStudentCountOmission(String nameSubject, Long idStudent, Integer course) {
        return repositoryOmissions.findBySubjectNameAndIdStudentCountOmission(nameSubject, idStudent, course);
    }

    public ResponseEntity addNewOmission(ControllerTeacher.StatusOmissionStudent statusOmissionStudent, BindingResult bindingResult) {
        User user = repositoryUser.findById(statusOmissionStudent.idStudent()).orElseThrow();
        Groups groups = repositoryGroups.findById(statusOmissionStudent.idGroup()).orElseThrow();
        Omissions omissions = repositoryOmissions.findByDateAndSubjectName(LocalDate.now(),statusOmissionStudent.nameSubject(), user.getId(), groups.getCurrentCourse());
        if (omissions!=null){
            String status;
            if (statusOmissionStudent.status()) {
                status = "Присутствует";
            } else {
                status = "Отсутствует";
            }
            omissions.setStatus(status);
            repositoryOmissions.save(omissions);
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            Integer currentCourse = groups.getCurrentCourse();
            String status;
            if (statusOmissionStudent.status()) {
                status = "Присутствует";
            } else {
                status = "Отсутствует";
            }
            LocalDate dateOmission = LocalDate.now();
            Integer numberMonth = dateOmission.getMonthValue();
            StudentsCourse studentsCourse = repositoryStudentCourse.findByUserIdAndCourse(user.getId(), currentCourse).orElseThrow();

            repositoryOmissions.save(new Omissions(studentsCourse, dateOmission, status, statusOmissionStudent.nameSubject(), statusOmissionStudent.numberCouple(), numberMonth));

            return new ResponseEntity(HttpStatus.OK);
        }
    }

    public Integer getOmissionsStudent(Long id) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
      return   repositoryOmissions.findBySubjectNameAndIdStudentCountOmission(id,startOfMonth,endOfMonth);
    }
}