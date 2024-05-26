package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<Students> students;

    @OneToMany(mappedBy = "group")
    private List<StudyProcess> studyProcesses;

    private Integer year;
    @Column(name = "current_course")
    private Integer currentCourse;
    @OneToMany(mappedBy = "group")
    private List<Courses> courses;
    @OneToMany(mappedBy = "group")
    private List<HomeWork> howeWorks;
    @ManyToOne
    @JoinColumn(name = "id_specialization")
    private Specialization specialization;
    @ManyToOne
    @JoinColumn(name = "id_curator")
    private Curator curator;

    public Groups(String name) {
        this.name = name;
    }


    public Groups(String name, Integer year, Integer currentCourse, Specialization specialization, Curator curator) {
        this.name = name;
        this.year = year;
        this.currentCourse = currentCourse;
        this.specialization = specialization;
        this.curator = curator;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}