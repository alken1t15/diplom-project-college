package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryFilesGroup extends JpaRepository<FilesGroup,Long>
{
    @Query("select f from FilesGroup f where f.course.course = ?1 and f.course.group.id = ?2")
    List<FilesGroup> findByCourseAndIdGroup(Integer course, Long idGroup);
}