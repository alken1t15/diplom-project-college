package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "week")
@Getter
@Setter
@ToString
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