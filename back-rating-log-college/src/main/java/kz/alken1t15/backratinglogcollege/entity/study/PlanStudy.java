package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "plan_study")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlanStudy {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type_study")
    private TypeStudy typeStudy;

    @ManyToOne
    @JoinColumn(name = "id_time_study")
    private TimeStudy timeStudy;

    @ManyToOne
    @JoinColumn(name = "id_subject_study")
    private SubjectStudy subjectStudy;

    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teachers teacher;

    @ManyToOne
    @JoinColumn(name = "id_auditorium")
    private Auditorium auditorium;

    @ManyToOne
    @JoinColumn(name = "id_week")
    private Week week;

@Column(name = "number_of_couple")
    private Integer numberOfCouple;


    public PlanStudy(TypeStudy typeStudy, TimeStudy timeStudy, SubjectStudy subjectStudy, Teachers teacher, Auditorium auditorium, Week week, Integer numberOfCouple) {
        this.typeStudy = typeStudy;
        this.timeStudy = timeStudy;
        this.subjectStudy = subjectStudy;
        this.teacher = teacher;
        this.auditorium = auditorium;
        this.week = week;
        this.numberOfCouple = numberOfCouple;
    }
}
