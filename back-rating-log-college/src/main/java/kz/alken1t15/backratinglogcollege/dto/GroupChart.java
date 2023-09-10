package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupChart {
    private String name;

    private Groups groups;

    private Long curse;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}