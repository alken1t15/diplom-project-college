package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepositoryStudentCourse extends JpaRepository<StudentsCourse, Long> {

    @Query("select s from StudentsCourse s where s.student.id=?1 and s.course=?2")
    Optional<StudentsCourse> findByUserIdAndCourse(Long studentId, Integer course);

}
