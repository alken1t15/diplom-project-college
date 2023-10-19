package kz.alken1t15.backratinglogcollege.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    @JsonIgnore
    private Groups group;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    private String login;

    private String password;

    @Column(name = "born_date")
    private LocalDate bornDate;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Evaluations> evaluations;
}