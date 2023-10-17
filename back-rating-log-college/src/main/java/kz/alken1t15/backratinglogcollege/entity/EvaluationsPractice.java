package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "evaluations_practice")
@ToString
public class EvaluationsPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Students student;

    @JoinColumn(name = "name_practice")
    private String namePractice;

    @Column(name = "date_evaluation")
    private LocalDate dateEvaluation;

    private Long ball;
}
