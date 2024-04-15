package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "howe_work")
@Getter
@Setter
public class HoweWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private String status;
    private String description;
    @Column(name = "name_subject")
    private String nameSubject;
    @ManyToOne
    @JoinColumn(name = "id_groups")
    private Groups group;
    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teachers teacher;
    @OneToMany(mappedBy = "howeWork")
    private List<TaskStudents> taskStudents;
}