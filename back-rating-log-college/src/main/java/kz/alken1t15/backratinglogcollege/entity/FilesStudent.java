package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "files_student")
@NoArgsConstructor
@Getter
@Setter
public class FilesStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_students")
    private Students student;

    @Column(name = "type_file")
    private String typeFile;

    @OneToMany(mappedBy = "filesStudent")
    private List<Omissions> omissions;
    @OneToMany(mappedBy = "filesStudent")
    private List<TaskStudentsFiles> taskStudentsFiles;

    public FilesStudent(String name, LocalDate dateCreate, Students student,String typeFile) {
        this.name = name;
        this.dateCreate = dateCreate;
        this.student = student;
        this.typeFile = typeFile;
    }
}