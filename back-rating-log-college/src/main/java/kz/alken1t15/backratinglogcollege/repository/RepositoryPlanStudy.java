package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.dto.work.PlanStudyFindDTO;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryPlanStudy extends JpaRepository<PlanStudy, Long> {
    @Query("select pt from PlanStudy pt where ?1 >= pt.typeStudy.dateStart and ?1 <=  pt.typeStudy.dateEnd and pt.week.id = ?2 and pt.typeStudy.studyProcess.group.id = ?3 order by pt.numberOfCouple")
    List<PlanStudy> findByOfDate(LocalDate date,Integer idWeek,Long idGroup);

    @Query("SELECT distinct  new kz.alken1t15.backratinglogcollege.dto.work.PlanStudyFindDTO(pt.subjectStudy.name, pt.teacher.firstName,pt.teacher.secondName) from PlanStudy pt where pt.typeStudy.id=?1")
    List<PlanStudyFindDTO> findByIdTypeStudy(Long id);


    @Query("select p from PlanStudy p join TypeStudy ts on ts.id = p.typeStudy.id join StudyProcess sp on sp.id = ts.studyProcess.id where sp.group.id = ?1 and p.week=?2 and ?3 between ts.dateStart and  ts.dateEnd")
    List<PlanStudy> findByGroupIdNameOfDay(Long idGroup,Long idWeek,LocalDate date);
}