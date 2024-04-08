package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerEvaluations;
import kz.alken1t15.backratinglogcollege.entity.Evaluations;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryEvaluations;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceEvaluations {
    private final RepositoryEvaluations repositoryEvaluations;
    private final RepositoryStudents repositoryStudents;

//    public HttpStatus save(ControllerEvaluations.Evaluation evaluation) {
//        Students students = repositoryStudents.findById(evaluation.idStudent()).orElse(null);
//        if (students == null) {
//            return HttpStatus.FORBIDDEN;
//        } else if (!StringUtils.isBlank(evaluation.nameObject()) || evaluation.dateEvaluation() != null || evaluation.ball() != 0) {
//            repositoryEvaluations.save(new Evaluations(students, evaluation.nameObject(), evaluation.dateEvaluation(), evaluation.ball()));
//            return HttpStatus.OK;
//        } else {
//            return HttpStatus.BAD_REQUEST;
//        }
//    }

    public List<Evaluations> findByDateEvaluation(LocalDate date,Long idStudent) {
        return repositoryEvaluations.findByDateEvaluation(date,idStudent);
    }
}