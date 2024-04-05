package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.service.ServiceEvaluations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/evaluations")
@AllArgsConstructor
public class ControllerEvaluations {
    private final ServiceEvaluations serviceEvaluations;

    @PostMapping(path = "/add")
    public HttpStatus addEvaluation(@RequestBody Evaluation evaluation){
//       return serviceEvaluations.save(evaluation);
        return null;
    }

    public record Evaluation(Long idStudent, String nameObject, LocalDate dateEvaluation, Long ball){ }
}