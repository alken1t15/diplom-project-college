package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "born_date")
    private LocalDate bornDate;

    @Column(name = "start_work")
    private LocalDate startWork;

//    @OneToMany(mappedBy = "teachers")
//    private List<Groups> groups;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY)
    private List<PlanStudy> planStudies;

    @OneToMany(mappedBy = "teacher")
    private List<HomeWork> howeWorks;

    public Teachers(String firstName, String secondName, String middleName, String login, String password, LocalDate bornDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.bornDate = bornDate;
    }
}
