package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
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
}