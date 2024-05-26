package kz.alken1t15.backratinglogcollege.entity.study.process;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "study_process")
@Getter
@Setter
@NoArgsConstructor
public class StudyProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_group")
    private Groups group;
    private Integer semester;
    private Integer course;
    @Column(name = "dateStart")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @OneToMany(mappedBy = "studyProcess")
    private List<TypeStudy> typeStudies;

    public StudyProcess(Groups group, Integer semester, Integer course, LocalDate dateStart, LocalDate dateEnd) {
        this.group = group;
        this.semester = semester;
        this.course = course;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}