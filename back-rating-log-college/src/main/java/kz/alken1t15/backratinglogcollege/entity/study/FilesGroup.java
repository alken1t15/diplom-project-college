package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "files_group")
@Getter
@Setter
public class FilesGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String file;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_courses")
    private Courses course;
}