package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_chart")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "")
    private Objects object;
}
