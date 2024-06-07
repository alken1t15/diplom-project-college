package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorySpecialization extends JpaRepository<Specialization,Long> {
    Specialization findByName(String name);
}
