package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task_students")
@Getter
@Setter
@NoArgsConstructor
public class TaskStudents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_howe_work")
    private HomeWork howeWork;
    @ManyToOne
    @JoinColumn(name = "id_students")
    private Students student;

    private Integer course;

    private Integer ball;

    @Column(name = "date_ball")
    private LocalDate dateBall;

    private String status;
    @Column(name = "time_completed")
    private LocalDateTime timeCompleted;

    @OneToMany(mappedBy = "taskStudent")
    private List<TaskStudentsFiles> taskStudentsFiles;

    public TaskStudents(HomeWork howeWork, Students student, Integer course, String status) {
        this.howeWork = howeWork;
        this.student = student;
        this.course = course;
        this.status = status;
    }
}