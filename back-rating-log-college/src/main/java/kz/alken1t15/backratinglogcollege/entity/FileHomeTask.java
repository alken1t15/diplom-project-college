package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "file_home_task")
@Getter
@Setter
@NoArgsConstructor
public class FileHomeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "id_home_task")
    private HomeWork homeWork;

    public FileHomeTask(String name, LocalDate dateCreate, HomeWork homeWork) {
        this.name = name;
        this.dateCreate = dateCreate;
        this.homeWork = homeWork;
    }
}
