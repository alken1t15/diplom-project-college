package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BullStudentDTO {
    @NotNull
    private Long id;
    @NotNull
    private Integer bull;
}