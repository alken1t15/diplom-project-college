package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "files_student")
public class FilesStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String file;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_students")
    private Students student;

    public FilesStudent(String name, String file, LocalDate dateCreate, Students student) {
        this.name = name;
        this.file = file;
        this.dateCreate = dateCreate;
        this.student = student;
    }
}