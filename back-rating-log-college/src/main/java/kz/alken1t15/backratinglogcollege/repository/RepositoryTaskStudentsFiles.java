package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.TaskStudentsFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryTaskStudentsFiles extends JpaRepository<TaskStudentsFiles,Long> {

    @Query("select t from TaskStudentsFiles t where t.taskStudent.howeWork.id=?1")
    List<TaskStudentsFiles> findByIdHomeWork(Long id);
}
