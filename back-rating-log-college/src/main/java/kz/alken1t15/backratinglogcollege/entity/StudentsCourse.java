package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "students_course")
@Getter
@Setter
@NoArgsConstructor
public class StudentsCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_students")
    private Students student;

    private Integer course;

    @OneToMany(mappedBy = "studentsCourse")
    private List<Omissions> omissions;

    @OneToMany(mappedBy = "studentsCourse")
    private List<Evaluations> evaluations;

    public StudentsCourse(Students student, Integer course) {
        this.student = student;
        this.course = course;
    }
}
