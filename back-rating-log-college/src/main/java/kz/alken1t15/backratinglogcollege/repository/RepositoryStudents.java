package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryStudents extends JpaRepository<Students,Long> {
}
