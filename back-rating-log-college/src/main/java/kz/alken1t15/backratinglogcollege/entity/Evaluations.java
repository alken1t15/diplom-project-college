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

    @ManyToOne
    @JoinColumn(name = "id_object")
    private Objects object;

    @ManyToOne
    @JoinColumn(name = "id_week")
    private Week week;

    private Integer grade;

    @Column(name = "date_grade")
    private LocalDate dateGrade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluations that = (Evaluations) o;
        return java.util.Objects.equals(student, that.student) && java.util.Objects.equals(object, that.object) && java.util.Objects.equals(week, that.week) && java.util.Objects.equals(grade, that.grade) && java.util.Objects.equals(dateGrade, that.dateGrade);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(student, object, week, grade, dateGrade);
    }
}