package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepositoryGroups extends JpaRepository<Groups,Long> {
    Optional<Groups> findByName(String name);

    @Query("select g from Groups  g join StudyProcess sp on sp.id = g.id join TypeStudy tp on tp.id = sp.id join PlanStudy pt on pt.id = tp.id where pt.teacher.id = ?1 and ?2 between sp.dateStart and  sp.dateEnd")
    List<Groups> findByAllGroupForTeacher(Long idTeacher, LocalDate date);
}
