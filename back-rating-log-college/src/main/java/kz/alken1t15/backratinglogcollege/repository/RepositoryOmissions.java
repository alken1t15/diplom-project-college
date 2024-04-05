package kz.alken1t15.backratinglogcollege.repository;

import kz.alken1t15.backratinglogcollege.entity.Omissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryOmissions extends JpaRepository<Omissions, Long> {
    @Query("select o from Omissions o where o.numberMonth = ?1 and o.studentsCourse.course = ?2 order by o.dateOmissions , o.numberCouple")
    List<Omissions> findByMonth(Integer numberMonth,Integer course);
}