package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.dto.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "omissions")
@ToString
public class Omissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Students student;

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

}