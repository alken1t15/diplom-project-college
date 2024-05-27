package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.service.ServiceTeachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepositoryGroups extends JpaRepository<Groups,Long> {
    Optional<Groups> findByName(String name);

    @Query("select new kz.alken1t15.backratinglogcollege.dto.teacher.CurrentGraphStudyGroup(g.name, pt.timeStudy.startLesson,pt.subjectStudy.name,g.id,pt.numberOfCouple)  from Groups g join StudyProcess sp on sp.group.id = g.id join TypeStudy tp on tp.studyProcess.id = sp.id join PlanStudy pt on pt.typeStudy.id = tp.id where pt.teacher.id = ?1 and ?2 between sp.dateStart and  sp.dateEnd and pt.week.id = ?3 order by pt.numberOfCouple")
    List<CurrentGraphStudyGroup> findByAllGroupForTeacher(Long idTeacher, LocalDate date, Long idWeek);


    @Query("select g from Groups g join StudyProcess sp on sp.group.id = g.id join TypeStudy ts on ts.studyProcess.id = sp.id join PlanStudy pt on pt.typeStudy.id = ts.id and pt.teacher.id = ?1")
    List<Groups> findByIdTeacher(Long id);


    @Query("select g from Groups g join StudyProcess sp on sp.group.id = g.id join TypeStudy ts on ts.studyProcess.id = sp.id join PlanStudy pt on pt.typeStudy.id = ts.id and g.id=?1 and pt.subjectStudy.id=?2")
    Groups findByIdGroupAndIdSubject(Long id,Long idSubject);

}
