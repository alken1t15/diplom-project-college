package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "specialization")
@Getter
@Setter
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "specialization")
    private List<Groups> groups;
}