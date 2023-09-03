package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "group_chart")
public class GroupChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private GroupChart groupChart;

    private Long curse;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;
}