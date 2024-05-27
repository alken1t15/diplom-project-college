package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "home_work")
@Getter
@Setter
@NoArgsConstructor
public class HomeWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
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
    @OneToMany(mappedBy = "homeWork")
    private List<FileHomeTask> fileHomeTasks;

    public HomeWork(LocalDate startDate, LocalDate endDate, String name, String description, String nameSubject, Groups group, Teachers teacher,String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.nameSubject = nameSubject;
        this.group = group;
        this.teacher = teacher;
        this.status = status;
    }
}