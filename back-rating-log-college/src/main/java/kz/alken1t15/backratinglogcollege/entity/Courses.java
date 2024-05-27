package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_groups")
    private Groups group;
    private Integer course;
    private Integer year;
    @OneToMany(mappedBy = "course")
    private List<FilesGroup> filesGroups;

    public Courses(Groups group, Integer course, Integer year) {
        this.group = group;
        this.course = course;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses courses = (Courses) o;
        return Objects.equals(course, courses.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course);
    }
}