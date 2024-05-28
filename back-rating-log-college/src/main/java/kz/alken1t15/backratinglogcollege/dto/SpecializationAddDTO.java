package kz.alken1t15.backratinglogcollege.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class SpecializationAddDTO {
    @NotNull
    private String name;
}
