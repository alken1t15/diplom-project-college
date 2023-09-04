package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_chart")
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
