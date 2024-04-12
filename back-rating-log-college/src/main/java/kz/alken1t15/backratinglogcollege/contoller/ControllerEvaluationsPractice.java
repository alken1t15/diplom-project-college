package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.service.ServiceEvaluationsPractice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/evaluations/practice")
@AllArgsConstructor
public class ControllerEvaluationsPractice {
//    private final ServiceEvaluationsPractice serviceEvaluationsPractice;
//
//    @PostMapping("/add")
//    public ResponseEntity addEvaluationsPractice(@RequestBody EvaluationsPractice evaluationsPractice){
//        return serviceEvaluationsPractice.addNewEvaluationsPractice(evaluationsPractice);
//    }
//
//    public record EvaluationsPractice(Long idStudent, String namePractice, LocalDate dateEvaluation, Long ball){ }
}
