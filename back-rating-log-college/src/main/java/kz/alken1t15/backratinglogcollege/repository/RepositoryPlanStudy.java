package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryPlanStudy extends JpaRepository<PlanStudy, Long> {
    @Query("select pt from PlanStudy pt where ?1 >= pt.typeStudy.dateStart and ?1 <=  pt.typeStudy.dateEnd and pt.week.id = ?2 and pt.typeStudy.studyProcess.group.id = ?3 order by pt.numberOfCouple")
    List<PlanStudy> findByOfDate(LocalDate date,Integer idWeek,Long idGroup);
}