package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "practice_study")
public class PracticeStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_graph")
    private Graph graph;
    private String name;
    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;
}