package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "objects")
public class Objects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
