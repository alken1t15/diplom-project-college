package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddBullStudentDTO {
    @NotNull
    private Long idSubject;
    @NotNull
    private LocalDate date;
    @NotNull
    private List<BullStudentDTO> students;
}
