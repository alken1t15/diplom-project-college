package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.HoweWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryHoweWork  extends JpaRepository<HoweWork,Long> {

    @Query("select h from HoweWork h where h.group.id = ?1 and h.id = ?2")
    HoweWork findByGroupAndId(Long idGroup,Long idHomeTask);
}
