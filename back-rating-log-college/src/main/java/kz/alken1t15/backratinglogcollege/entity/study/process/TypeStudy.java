package kz.alken1t15.backratinglogcollege.entity.study.process;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "type_study")
@Getter
@Setter
public class TypeStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "dateStart")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @ManyToOne
    @JoinColumn(name = "id_study_process")
    private StudyProcess studyProcess;
    @OneToMany(mappedBy = "typeStudy",fetch = FetchType.LAZY)
    private List<PlanStudy> planStudies;
}
