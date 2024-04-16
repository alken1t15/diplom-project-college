package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryHoweWork  extends JpaRepository<HomeWork,Long> {

    @Query("select h from HomeWork h where  h.id = ?1 order by h.endDate desc ")
    HomeWork findByGroupAndId(Long idHomeTask);

}
