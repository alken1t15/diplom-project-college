package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.TimeStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTimeStudy extends JpaRepository<TimeStudy,Long> {
}
