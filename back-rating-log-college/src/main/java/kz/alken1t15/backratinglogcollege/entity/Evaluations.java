package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluations")
public class Evaluations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "id_object")
    private Objects object;

    @ManyToOne
    @JoinColumn(name = "id_week")
    private Week week;

    private Integer grade;

    @Column(name = "date_grade")
    private LocalDate dateGrade;
}
