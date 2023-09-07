package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "objects")
@Getter
@Setter
@ToString
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
