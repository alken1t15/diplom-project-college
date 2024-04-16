package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_students")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "id_file_students")
    private FilesStudent filesStudent;

    private String status;
    @Column(name = "time_completed")
    private LocalDateTime timeCompleted;

}