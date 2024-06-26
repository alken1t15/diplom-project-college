package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subject_study")
@Getter
@Setter
@NoArgsConstructor
public class SubjectStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "subjectStudy")
    private List<PlanStudy> planStudies;

    public SubjectStudy(String name) {
        this.name = name;
    }
}
