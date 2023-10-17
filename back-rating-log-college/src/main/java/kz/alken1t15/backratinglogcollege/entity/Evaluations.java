package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@ToString
public class Evaluations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Students student;

    @JoinColumn(name = "name_object")
    private String nameObject;

    @Column(name = "date_evaluation")
    private LocalDate dateEvaluation;

    private Long ball;
}