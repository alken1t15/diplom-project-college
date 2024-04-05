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
@NoArgsConstructor
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "id_teacher")
//    private Teachers teachers;

    @OneToMany(mappedBy = "group")
    private List<Students> students;

    private Integer year;

    private Integer course;

    public Groups(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}