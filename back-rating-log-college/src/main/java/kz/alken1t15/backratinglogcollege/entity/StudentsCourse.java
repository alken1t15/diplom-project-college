package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "students_course")
@Getter
@Setter
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
}
