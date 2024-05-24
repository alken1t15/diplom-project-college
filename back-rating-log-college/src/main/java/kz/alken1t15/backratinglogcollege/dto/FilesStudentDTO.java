package kz.alken1t15.backratinglogcollege.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kz.alken1t15.backratinglogcollege.entity.Students;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilesStudentDTO {
    private Long id;
    private byte[] file;
    private LocalDate dateCreate;
    private String typeFile;

    public FilesStudentDTO(Long id, byte[] file, LocalDate dateCreate, String typeFile) {
        this.id = id;
        this.file = file;
        this.dateCreate = dateCreate;
        this.typeFile = typeFile;
    }
}