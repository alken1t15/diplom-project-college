package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Objects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryObjects extends JpaRepository<Objects,Long> {
    Optional<Objects> findByName(String name);
}
