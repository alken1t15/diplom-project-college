package kz.alken1t15.backratinglogcollege.entity.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "time_study")
@Getter
@Setter
public class TimeStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_lesson")
    private LocalTime startLesson;
    @Column(name = "end_lesson")
    private LocalTime endLesson;

    @OneToMany(mappedBy = "timeStudy")
    private List<PlanStudy> planStudies;
}