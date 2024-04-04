package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    private String login;

    private String password;

    @Column(name = "born_date")
    private LocalDate bornDate;

//    @OneToMany(mappedBy = "teachers")
//    private List<Groups> groups;

    public Teachers(String firstName, String secondName, String middleName, String login, String password, LocalDate bornDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.bornDate = bornDate;
    }
}
