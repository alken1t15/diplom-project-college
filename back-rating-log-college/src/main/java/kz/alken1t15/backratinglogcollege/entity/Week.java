package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "week")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "week")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "week")
    private List<Evaluations> evaluations;
}
