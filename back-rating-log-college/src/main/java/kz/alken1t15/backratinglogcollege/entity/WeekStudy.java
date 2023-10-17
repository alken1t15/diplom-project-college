package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import kz.alken1t15.backratinglogcollege.dto.Week;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "week_study")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeekStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_graph")
    private Graph graph;

    @Enumerated(EnumType.STRING)
    private Week name;

    @Column(name = "first_para")
    private String firstPara;

    @Column(name = "second_para")
    private String secondPara;

    @Column(name = "third_para")
    private String thirdPara;

    @Column(name = "fourth_para")
    private String fourthPara;

    @Column(name = "fifth_para")
    private String fifthPara;

    @Column(name = "sixth_para")
    private String sixthPara;
}