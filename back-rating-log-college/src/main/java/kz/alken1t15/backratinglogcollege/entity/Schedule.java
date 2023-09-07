package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "group_chart")
@Getter
@Setter
@ToString
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_object")
    private Objects object;

    @ManyToOne
    @JoinColumn(name = "id_group_chart")
    private GroupChart groupChart;

    @ManyToOne
    @JoinColumn(name = "id_week")
    private Week week;

    private Integer couple;
}
