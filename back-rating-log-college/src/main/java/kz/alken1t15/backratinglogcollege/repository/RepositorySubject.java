package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorySubject extends JpaRepository<SubjectStudy,Long> {
    SubjectStudy findByName(String name);

    @Query("SELECT s from SubjectStudy s join PlanStudy pt on pt.subjectStudy.id = s.id where pt.teacher.id=?1")
    List<SubjectStudy> findByIdTeacher(Long id);
}
