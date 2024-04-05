package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "auditorium")
@Getter
@Setter
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer block;
    private Integer floor;
    private Integer cabinet;
    @OneToMany(mappedBy = "auditorium")
    private List<PlanStudy> planStudies;
}
