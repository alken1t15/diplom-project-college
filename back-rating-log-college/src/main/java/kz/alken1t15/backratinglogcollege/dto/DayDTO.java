package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayDTO {
    private String date;
    private String nameOfDay;
    private List<TardinessDTO> tardiness;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayDTO dayDTO = (DayDTO) o;
        return Objects.equals(date, dayDTO.date) && Objects.equals(nameOfDay, dayDTO.nameOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, nameOfDay);
    }
}