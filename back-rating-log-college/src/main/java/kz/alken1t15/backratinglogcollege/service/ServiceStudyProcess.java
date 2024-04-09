package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.MonthDTO;
import kz.alken1t15.backratinglogcollege.dto.work.MonthReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessDTO;
import kz.alken1t15.backratinglogcollege.dto.work.ProcessReturnDTO;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudyProcess;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceStudyProcess {
    private RepositoryStudyProcess repositoryStudyProcess;
    private RepositoryStudents repositoryStudents;
    private RepositoryUser repositoryUser;


    public ProcessReturnDTO getStudyProcess(ProcessDTO process) {
      SecurityContext securityContext = SecurityContextHolder.getContext();
      User user = repositoryUser.findByLogin(securityContext.getAuthentication().getName()).orElseThrow();
        Students student = repositoryStudents.findById(user.getId()).orElseThrow();
        StudyProcess studyProcess;
        if (process.getSemester()!=null && process.getCourse()!= null){
            studyProcess = repositoryStudyProcess.findByCourseSemesterGroup(process.getCourse(), process.getSemester(),student.getGroup().getId());
        }
        else {
            studyProcess = repositoryStudyProcess.findByCourseSemesterGroup(student.getGroup().getCurrentCourse(),1,student.getGroup().getId());
        }
        ProcessReturnDTO processReturnDTO = new ProcessReturnDTO();

        if (process.getSemester()!=null && process.getCourse()!= null){
            processReturnDTO.setCurrentCourse(process.getCourse());
            processReturnDTO.setCurrentSemester(process.getSemester());
        }
        else {
            processReturnDTO.setCurrentCourse(student.getGroup().getCurrentCourse());
            processReturnDTO.setCurrentSemester(1);
        }
        processReturnDTO.setTotalCourse(student.getGroup().getCourses().size());

        List<MonthReturnDTO> months = getAllMonths(studyProcess.getDateStart(),studyProcess.getDateEnd());

        processReturnDTO.setMonths(months);

        return processReturnDTO;
    }


    private static List<MonthReturnDTO> getAllMonths(LocalDate startDate, LocalDate endDate) {
        String[] arrMonth = new String[]{"01 Янв","02 Фев","03 Март","04 Апр", "05 Май","06 Июн","","","09 Сен","10 Окт","11 Нояб", "12 Дек"};

        List<MonthReturnDTO> months = new ArrayList<>();
        LocalDate currentMonth = startDate.withDayOfMonth(1);

        while (!currentMonth.isAfter(endDate.withDayOfMonth(1))) {
            months.add(new MonthReturnDTO(arrMonth[currentMonth.getMonth().getValue()-1],currentMonth.getMonth().getValue()));
            currentMonth = currentMonth.plusMonths(1);
        }

        return months;
    }
}
