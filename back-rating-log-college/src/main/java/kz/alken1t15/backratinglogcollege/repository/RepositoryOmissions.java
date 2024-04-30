package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Omissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryOmissions extends JpaRepository<Omissions, Long> {
    @Query("select o from Omissions o where o.numberMonth = ?1 and o.studentsCourse.course = ?2 and o.studentsCourse.student.id = ?3 order by o.dateOmissions , o.numberCouple")
    List<Omissions> findByMonth(Integer numberMonth, Integer course, Long idStudent);

    @Query("select o from Omissions  o where o.dateOmissions = ?1 and o.nameObject= ?2 and o.studentsCourse.student.id = ?3 and o.studentsCourse.course = ?4 and o.filesStudent =null ")
    Omissions findByDateAndSubjectName(LocalDate date, String nameSubject, Long idStudent, Integer course);

    @Query("select o from Omissions  o where o.dateOmissions = ?1 and o.nameObject= ?2 and o.studentsCourse.student.id = ?3 and o.studentsCourse.course = ?4 and o.filesStudent !=null  ")
    Omissions findByDateAndSubjectNameAndCertificate(LocalDate date, String nameSubject, Long idStudent, Integer course);


    @Query("select count(o) from Omissions  o where  o.nameObject= ?1 and o.studentsCourse.student.id = ?2 and o.studentsCourse.course = ?3")
    Integer findBySubjectNameAndIdStudentCountOmission(String nameSubject, Long idStudent, Integer course);
}