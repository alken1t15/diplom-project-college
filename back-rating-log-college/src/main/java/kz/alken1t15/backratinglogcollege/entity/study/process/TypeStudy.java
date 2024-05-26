package kz.alken1t15.backratinglogcollege.entity.study.process;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "type_study")
@Getter
@Setter
@NoArgsConstructor
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

    public TypeStudy(String name, LocalDate dateStart, LocalDate dateEnd, StudyProcess studyProcess) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.studyProcess = studyProcess;
    }
}
