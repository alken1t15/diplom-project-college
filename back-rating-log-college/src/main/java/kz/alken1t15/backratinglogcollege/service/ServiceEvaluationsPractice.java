package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.contoller.ControllerEvaluationsPractice;
import kz.alken1t15.backratinglogcollege.repository.RepositoryEvaluationsPractice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceEvaluationsPractice {
    private RepositoryEvaluationsPractice evaluationsPractice;

//    public ResponseEntity addNewEvaluationsPractice(ControllerEvaluationsPractice.EvaluationsPractice evaluationsPractice) {
//        return null;
//    }
}
