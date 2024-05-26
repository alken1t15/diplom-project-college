package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "auditorium")
@Getter
@Setter
@NoArgsConstructor
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer block;
    private Integer floor;
    private Integer cabinet;
    @OneToMany(mappedBy = "auditorium")
    private List<PlanStudy> planStudies;

    public Auditorium(Integer block, Integer floor, Integer cabinet) {
        this.block = block;
        this.floor = floor;
        this.cabinet = cabinet;
    }
}
