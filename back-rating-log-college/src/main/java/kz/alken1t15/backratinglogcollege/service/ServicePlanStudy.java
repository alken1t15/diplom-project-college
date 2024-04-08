package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.AuditoriumDTO;
import kz.alken1t15.backratinglogcollege.dto.PlanStudyDTO;
import kz.alken1t15.backratinglogcollege.dto.PlanStudySubjectDTO;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryPlanStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicePlanStudy {
    @Autowired
    private RepositoryPlanStudy repositoryPlanStudy;
    private final String[] russianDayOfWeekNames = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    private final String[] russianMonthNames = {
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
    };

    public PlanStudyDTO findByOfDate(Long idGroup) {
        LocalDate date = LocalDate.now();

        int dayOfWeekIndex = date.getDayOfWeek().getValue() - 1;
        String dayOfWeekName = russianDayOfWeekNames[dayOfWeekIndex];

        Month month = date.getMonth();
        String monthName = russianMonthNames[month.getValue() - 1];

        List<PlanStudy> planStudies = repositoryPlanStudy.findByOfDate(date, dayOfWeekIndex + 1,idGroup);

        List<PlanStudySubjectDTO> subjects = new ArrayList<>();

        for (PlanStudy p : planStudies) {
            subjects.add(new PlanStudySubjectDTO(
                    p.getSubjectStudy().getName(),
                    new AuditoriumDTO(
                            p.getAuditorium().getBlock(),
                            p.getAuditorium().getFloor(),
                            p.getAuditorium().getFloor()),
                    p.getTimeStudy().getStartLesson(),
                    p.getTimeStudy().getEndLesson()));
        }
        return new PlanStudyDTO(date.getDayOfMonth() + " " + monthName, dayOfWeekName, subjects);
    }
}