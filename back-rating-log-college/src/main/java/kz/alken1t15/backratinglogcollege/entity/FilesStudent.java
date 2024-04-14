package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "files_student")
@NoArgsConstructor
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

    @Column(name = "type_file")
    private String typeFile;

    @OneToMany(mappedBy = "filesStudent")
    private List<Omissions> omissions;

    public FilesStudent(String name, String file, LocalDate dateCreate, Students student,String typeFile) {
        this.name = name;
        this.file = file;
        this.dateCreate = dateCreate;
        this.student = student;
    }
}