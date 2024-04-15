package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task_students")
public class TaskStudents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_howe_work")
    private HoweWork howeWork;
    @ManyToOne
    @JoinColumn(name = "id_students")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "id_file_students")
    private FilesStudent filesStudent;

    private String status;

}