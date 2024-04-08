package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryEvaluations extends JpaRepository<Evaluations,Long> {
    @Query("select e from Evaluations e where e.dateEvaluation = ?1 and e.studentsCourse.student.id = ?2")
    List<Evaluations> findByDateEvaluation(LocalDate dateEvaluation,Long idStudent);
}
