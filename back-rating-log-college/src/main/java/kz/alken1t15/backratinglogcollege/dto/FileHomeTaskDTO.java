package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FileHomeTaskDTO {
    private Long id;
    private String name;
    private LocalDate dateCreate;
    private byte[] file;
}