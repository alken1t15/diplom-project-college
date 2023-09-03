package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}