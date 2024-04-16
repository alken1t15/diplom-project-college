package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "file_home_task")
@Getter
@Setter
public class FileHomeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String file;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_home_task")
    private HomeWork homeWork;
}
