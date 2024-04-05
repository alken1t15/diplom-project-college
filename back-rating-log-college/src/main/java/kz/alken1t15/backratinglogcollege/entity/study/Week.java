package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "week")
@Getter
@Setter
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "week")
    private List<PlanStudy> planStudies;
}