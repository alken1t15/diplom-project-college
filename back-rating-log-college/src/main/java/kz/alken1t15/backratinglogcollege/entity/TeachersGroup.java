package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "teachers_group")
@Getter
@Setter
@ToString
public class TeachersGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teachers teachers;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Groups groups;

    @Column(name = "name_object")
    private String nameObject;

    private Long course;

    private Long semester;

    public TeachersGroup(Teachers teachers, Groups groups, String nameObject, Long course, Long semester) {
        this.teachers = teachers;
        this.groups = groups;
        this.nameObject = nameObject;
        this.course = course;
        this.semester = semester;
    }
}