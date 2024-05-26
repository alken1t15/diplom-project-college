package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "curator")
@NoArgsConstructor
public class Curator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_teacher")
    private Teachers teacher;

    @OneToMany(mappedBy = "curator")
    private List<Groups> groups;

    public Curator(Teachers teacher) {
        this.teacher = teacher;
    }
}