package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryGroups extends JpaRepository<Groups,Long> {
    Optional<Groups> findByName(String name);
}
