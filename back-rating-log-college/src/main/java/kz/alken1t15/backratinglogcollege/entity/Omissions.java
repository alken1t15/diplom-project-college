package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.dto.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "omissions")
@ToString
@NoArgsConstructor
public class Omissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private StudentsCourse studentsCourse;

    @Column(name = "date_omissions")
    private LocalDate dateOmissions;

//    @Enumerated(EnumType.STRING)
    private String status;

    @Column(name = "name_object")
    private String nameObject;

    @Column(name = "number_couple")
    private Integer numberCouple;

    @Column(name = "number_month")
    private Integer numberMonth;
    @ManyToOne
    @JoinColumn(name = "id_files_student")
    private FilesStudent filesStudent;


    public Omissions(StudentsCourse studentsCourse, LocalDate dateOmissions, String status, String nameObject, Integer numberCouple, Integer numberMonth, FilesStudent filesStudent) {
        this.studentsCourse = studentsCourse;
        this.dateOmissions = dateOmissions;
        this.status = status;
        this.nameObject = nameObject;
        this.numberCouple = numberCouple;
        this.numberMonth = numberMonth;
        this.filesStudent = filesStudent;
    }

    public Omissions(StudentsCourse studentsCourse, LocalDate dateOmissions, String status, String nameObject, Integer numberCouple, Integer numberMonth) {
        this.studentsCourse = studentsCourse;
        this.dateOmissions = dateOmissions;
        this.status = status;
        this.nameObject = nameObject;
        this.numberCouple = numberCouple;
        this.numberMonth = numberMonth;
    }
}