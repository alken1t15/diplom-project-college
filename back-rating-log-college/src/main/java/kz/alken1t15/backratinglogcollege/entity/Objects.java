package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "objects")
public class Objects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "object")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "object")
    private List<Evaluations> evaluations;
}
