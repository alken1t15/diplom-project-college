package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.AuditoriumDTO;
import kz.alken1t15.backratinglogcollege.dto.PlanStudyAddDTO;
import kz.alken1t15.backratinglogcollege.dto.PlanStudyDTO;
import kz.alken1t15.backratinglogcollege.dto.PlanStudySubjectDTO;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryPlanStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicePlanStudy {
    @Autowired
    private RepositoryPlanStudy repositoryPlanStudy;
    private ServiceTypeStudy serviceTypeStudy;
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

        List<PlanStudy> planStudies = repositoryPlanStudy.findByOfDate(date, dayOfWeekIndex + 1, idGroup);

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

    public List<PlanStudy> getPlanStudyToday(Long idGroup) {
        LocalDate date = LocalDate.now();
        int dayOfWeekIndex = date.getDayOfWeek().getValue();
        return repositoryPlanStudy.findByOfDate(date, dayOfWeekIndex, idGroup);
    }


    public List<PlanStudy> getPlanStudyByGroupIdAndCurrentDay(Long idGroup) {
        LocalDate date = LocalDate.now();
        long idWeek = date.getDayOfWeek().getValue();
        return repositoryPlanStudy.findByGroupIdNameOfDay(idGroup, idWeek, date);
    }

    //В разработке
    public ResponseEntity saveNewPlanStudy(PlanStudyAddDTO planStudy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        TypeStudy typeStudy = serviceTypeStudy.findById(planStudy.getIdTypeStudy());
        //
        return null;
    }
}