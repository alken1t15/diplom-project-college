package kz.alken1t15.backratinglogcollege.dto.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FileRequestDTO {
    private Long id;
    private String name;
    private String typeFile;
    private LocalDate date;

    public FileRequestDTO(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}