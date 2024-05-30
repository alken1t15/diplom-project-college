package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Students {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group")
    private Groups group;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "born_date")
    private LocalDate bornDate;

    @OneToMany(mappedBy = "student")
    private List<StudentsCourse> studentsCourses;
    @OneToMany(mappedBy = "student")
    private List<FilesStudent> filesStudents;
    @OneToMany(mappedBy = "student")
    private List<TaskStudents> taskStudents;
    @Column(name = "subgroup_name")
    private String subgroupName;

    public Students(Long id, Groups group, String firstName, String secondName, String middleName, LocalDate bornDate, String subgroupName) {
        this.id = id;
        this.group = group;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.bornDate = bornDate;
        this.subgroupName = subgroupName;
    }
}