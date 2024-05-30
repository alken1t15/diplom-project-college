package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "files_group")
@Getter
@Setter
@NoArgsConstructor
public class FilesGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_courses")
    private Courses course;
    @Column(name = "subject_name")
    private String subjectName;
    private String description;

    public FilesGroup(String name, LocalDate dateCreate, Courses course, String subjectName, String description) {
        this.name = name;
        this.dateCreate = dateCreate;
        this.course = course;
        this.subjectName = subjectName;
        this.description = description;
    }
}