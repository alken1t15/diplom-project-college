package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.TaskStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryTaskStudents extends JpaRepository<TaskStudents, Long> {
    @Query("select t from TaskStudents t where t.student.id=?1 and  t.status=?2")
    List<TaskStudents> findByStudentAndStatus(Long idStudent, String textStatus);

    @Query("select t from  TaskStudents t where t.howeWork.id = ?1 and  t.student.id = ?2")
    TaskStudents findByIdWorkAndIdStudent(Long idHomeWord, Long idStudentId);
}
