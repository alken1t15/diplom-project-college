package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.DayDTO;
import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.TardinessDTO;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.repository.RepositoryOmissions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceOmissions {
    private RepositoryOmissions repositoryOmissions;

    public MonthDTO findByMonth(Integer numberMonth) {
        List<DayDTO> dayDTOs = new ArrayList<>();
        List<Omissions> omissions = repositoryOmissions.findByMonth(numberMonth);
        String[] russianDayOfWeekNames = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        String[] russianMonthNames = {
                "января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"
        };
//        int countDay=0;
//        for (Omissions o : omissions){
//            String statusText = o.getStatus();
//            String nameObject = o.getNameObject();
//            LocalDate localDate = o.getDateOmissions();
//            String nameOfDay = localDate.getDayOfWeek().name();
//            if (dayDTO==null){
//
//            }
//        }
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


        return new MonthDTO("Сентябрь", dayDTOs);
    }
}