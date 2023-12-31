package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.TeachersGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryTeachersGroup extends JpaRepository<TeachersGroup,Long> {
    Optional<TeachersGroup> findByNameObjectAndCourseAndSemester(String nameObject, Long course, Long semester);
}
