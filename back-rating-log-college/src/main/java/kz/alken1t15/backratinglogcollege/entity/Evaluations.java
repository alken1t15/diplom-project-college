package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Evaluations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private StudentsCourse studentsCourse;

    @JoinColumn(name = "name_object")
    private String nameObject;

    @Column(name = "date_evaluation")
    private LocalDate dateEvaluation;

    private Long ball;

    @Column(name = "name_teacher")
    private String nameTeacher;

}