package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepositoryStudents extends JpaRepository<Students,Long> {

    @Query("select s from  Students s where  s.id = ?1")
    Optional<Students> findByIdCustom(Long id);

    Students findByFirstNameAndSecondName(String firstName, String secondName);
}
