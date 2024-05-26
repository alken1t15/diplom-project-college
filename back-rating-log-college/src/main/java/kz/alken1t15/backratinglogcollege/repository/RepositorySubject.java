package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorySubject extends JpaRepository<SubjectStudy,Long> {
    SubjectStudy findByName(String name);
}
