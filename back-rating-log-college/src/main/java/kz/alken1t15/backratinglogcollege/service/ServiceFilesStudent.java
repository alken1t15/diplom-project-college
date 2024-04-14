package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.work.FilesStudentRequestDTO;
import kz.alken1t15.backratinglogcollege.entity.FilesStudent;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFilesStudent;
import kz.alken1t15.backratinglogcollege.repository.RepositoryOmissions;
import kz.alken1t15.backratinglogcollege.repository.RepositoryPlanStudy;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceFilesStudent {
    private final RepositoryFilesStudent repositoryFilesStudent;
    private final ServiceStudents serviceStudent;
    private final ServicePlanStudy servicePlanStudy;
    private final RepositoryOmissions repositoryOmissions;


    public ResponseEntity save(FilesStudentRequestDTO file) {
        Students student = serviceStudent.getStudent();
        StudentsCourse studentsCourse = new StudentsCourse();
        for (StudentsCourse s : student.getStudentsCourses()) {
            if (student.getGroup().getCurrentCourse() == s.getCourse()) {
                studentsCourse = s;
            }
        }
        if (!StringUtils.isBlank(file.getFile()) && !StringUtils.isBlank(file.getName()) && !StringUtils.isBlank(file.getTypeFile())) {
            repositoryFilesStudent.save(new FilesStudent(file.getName(), file.getFile(), LocalDate.now(), student, file.getTypeFile()));
            FilesStudent filesStudent = repositoryFilesStudent.findByDateCreateAndIdStudents(LocalDate.now(), student.getId());
            List<PlanStudy> planStudies = servicePlanStudy.getPlanStudyToday(student.getGroup().getId());
            for (PlanStudy p : planStudies) {
                repositoryOmissions.save(new Omissions(studentsCourse, LocalDate.now(), "С справкой", p.getSubjectStudy().getName(), p.getNumberOfCouple(), LocalDate.now().getMonthValue(), filesStudent));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Не правильный запрос", HttpStatus.BAD_REQUEST);
        }
    }
}