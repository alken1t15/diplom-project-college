package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teachers teachers;

    @OneToMany(mappedBy = "group")
    private List<Students> students;

    public Groups(String name) {
        this.name = name;
    }


}