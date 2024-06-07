package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Curator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryCurator extends JpaRepository<Curator,Long> {

    @Query("select c from Curator c where  c.teacher.id = ?1")
    Curator findByIdTeacher(Long id);
}
