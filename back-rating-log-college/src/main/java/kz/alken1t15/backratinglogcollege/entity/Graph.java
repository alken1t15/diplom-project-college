package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.dto.Semester;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "graph")
@ToString
public class Graph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Groups groups;

    @Enumerated(EnumType.ORDINAL)
    private Semester semester;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;
}