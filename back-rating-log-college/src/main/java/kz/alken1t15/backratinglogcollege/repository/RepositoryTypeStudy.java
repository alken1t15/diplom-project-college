package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryTypeStudy extends JpaRepository<TypeStudy,Long> {
    @Query ("select t.id from TypeStudy  t where t.studyProcess.id = ?1")
    List<Long> findByIdStudyProcess(Long id);
}