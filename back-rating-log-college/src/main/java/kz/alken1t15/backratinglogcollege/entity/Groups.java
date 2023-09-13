package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<Students> students;

    @OneToMany(mappedBy = "groups")
    private List<GroupChart> groupCharts;

    public Groups(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(name, groups.name) && Objects.equals(students, groups.students) && Objects.equals(groupCharts, groups.groupCharts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, students, groupCharts);
    }
}