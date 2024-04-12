package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryStudyProcess extends JpaRepository<StudyProcess, Long> {

    @Query("select p from StudyProcess p where p.course =?1 and  p.semester=?2 and p.group.id = ?3")
    StudyProcess findByCourseSemesterGroup(Integer course, Integer semester, Long idGroup);

    @Query("select p from StudyProcess p where p.course =?1 and p.group.id = ?2 order by p.semester")
    List<StudyProcess> findByCourseGroup(Integer course, Long idGroup);
}