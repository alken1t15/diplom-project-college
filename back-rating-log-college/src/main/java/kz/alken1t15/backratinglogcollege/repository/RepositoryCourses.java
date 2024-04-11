package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryCourses extends JpaRepository<Courses,Long> {
    @Query("select  c from Courses c where c.course = ?1 and c.group.id = ?2")
    Courses findByCourseGroup(Integer course,Long idGroup);
}
