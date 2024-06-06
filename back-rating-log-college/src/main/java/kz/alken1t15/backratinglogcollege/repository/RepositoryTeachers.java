package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.entity.TeachersGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryTeachers extends JpaRepository<Teachers,Long> {
    Teachers findByFirstNameAndSecondName(String firstName, String secondName);

}
