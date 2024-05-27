package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryEvaluations extends JpaRepository<Evaluations,Long> {
    @Query("select e from Evaluations e where e.dateEvaluation = ?1 and e.studentsCourse.student.id = ?2")
    List<Evaluations> findByDateEvaluation(LocalDate dateEvaluation,Long idStudent);

    @Query("select e from Evaluations  e where  ?1 >= e.dateEvaluation and  ?2<=e.dateEvaluation  and  e.studentsCourse.course = ?3 and e.studentsCourse.student.id = ?4")
    List<Evaluations> findByDateStudentCourse(LocalDate start, LocalDate end, Integer course, Long idStudent);

    @Query("select distinct e.nameObject  from Evaluations  e where   e.dateEvaluation between ?1 and ?2 and  e.studentsCourse.course = ?3 and e.studentsCourse.student.id = ?4")
    List<String> findByDateStudentCourseDistinctName(LocalDate start, LocalDate end, Integer course, Long idStudent);

    @Query("select e from Evaluations  e where   e.dateEvaluation between ?1 and ?2  and  e.studentsCourse.course = ?3 and e.studentsCourse.student.id = ?4 and e.nameObject = ?5 order by e.dateEvaluation")
    List<Evaluations> findByDateStudentCourseNameObject(LocalDate start, LocalDate end, Integer course, Long idStudent,String nameObject);


    @Query("select distinct e.nameObject  from Evaluations  e where   e.dateEvaluation between ?1 and ?2 and e.studentsCourse.student.id = ?3")
    List<String> findByDateStudentCourseDistinctName(LocalDate start, LocalDate end, Long idStudent);


    @Query("select e  from Evaluations  e where   e.dateEvaluation between ?1 and ?2 and e.studentsCourse.student.id = ?3 and e.nameObject = ?4")
    List<Evaluations> findByDateStudentCourseSubjectName(LocalDate start, LocalDate end, Long idStudent,String nameSubject);
}
