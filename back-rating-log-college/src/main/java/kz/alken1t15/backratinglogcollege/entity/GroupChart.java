package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "group_chart")
@Getter
@Setter
@ToString
public class GroupChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Groups groups;

    private Long curse;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @OneToMany(mappedBy = "groupChart")
    private List<Schedule> schedules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupChart that = (GroupChart) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(groups, that.groups) && Objects.equals(curse, that.curse) && Objects.equals(dateStart, that.dateStart) && Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, groups, curse, dateStart);
    }
}