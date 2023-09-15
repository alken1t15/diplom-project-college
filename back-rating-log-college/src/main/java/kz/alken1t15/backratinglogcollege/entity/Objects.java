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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objects objects = (Objects) o;
        return java.util.Objects.equals(name, objects.name) && java.util.Objects.equals(schedules, objects.schedules) && java.util.Objects.equals(evaluations, objects.evaluations);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, schedules, evaluations);
    }
}