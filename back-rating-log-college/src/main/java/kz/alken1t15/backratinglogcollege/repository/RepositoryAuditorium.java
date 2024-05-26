package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.study.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAuditorium extends JpaRepository<Auditorium,Long> {
    Auditorium findByBlockAndFloorAndCabinet(Integer block, Integer floor, Integer cabinet);
}
