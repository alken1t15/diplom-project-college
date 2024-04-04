package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryEvaluations extends JpaRepository<Evaluations,Long> {
    List<Evaluations> findByDateEvaluation(LocalDate dateEvaluation);
}
