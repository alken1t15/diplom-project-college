package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.dto.file.FileRequestDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FileDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FilesStudentRequestDTO;
import kz.alken1t15.backratinglogcollege.entity.FilesStudent;
import kz.alken1t15.backratinglogcollege.entity.Omissions;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import kz.alken1t15.backratinglogcollege.entity.study.PlanStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFilesStudent;
import kz.alken1t15.backratinglogcollege.repository.RepositoryOmissions;
import kz.alken1t15.backratinglogcollege.repository.RepositoryPlanStudy;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceFilesStudent {
    @Autowired
    private final RepositoryFilesStudent repositoryFilesStudent;
    @Autowired
    private final ServiceStudents serviceStudent;
    @Autowired
    private final ServicePlanStudy servicePlanStudy;
    @Autowired
    private final RepositoryOmissions repositoryOmissions;

    public ServiceFilesStudent(RepositoryFilesStudent repositoryFilesStudent, ServiceStudents serviceStudent, ServicePlanStudy servicePlanStudy, RepositoryOmissions repositoryOmissions) {
        this.repositoryFilesStudent = repositoryFilesStudent;
        this.serviceStudent = serviceStudent;
        this.servicePlanStudy = servicePlanStudy;
        this.repositoryOmissions = repositoryOmissions;
    }

    @Value("${path.save.file}")
    private String pathSaveFile;


//    public ResponseEntity save(FilesStudentRequestDTO file) {
//        Students student = serviceStudent.getStudent();
//        StudentsCourse studentsCourse = new StudentsCourse();
//        for (StudentsCourse s : student.getStudentsCourses()) {
//            if (student.getGroup().getCurrentCourse() == s.getCourse()) {
//                studentsCourse = s;
//            }
//        }
//        if (!StringUtils.isBlank(file.getFile()) && !StringUtils.isBlank(file.getName()) && !StringUtils.isBlank(file.getTypeFile())) {
//            repositoryFilesStudent.save(new FilesStudent(file.getName(), file.getFile(), LocalDate.now(), student, file.getTypeFile()));
//            FilesStudent filesStudent = repositoryFilesStudent.findByDateCreateAndIdStudents(LocalDate.now(), student.getId());
//            List<PlanStudy> planStudies = servicePlanStudy.getPlanStudyToday(student.getGroup().getId());
//            for (PlanStudy p : planStudies) {
//                repositoryOmissions.save(new Omissions(studentsCourse, LocalDate.now(), "С справкой", p.getSubjectStudy().getName(), p.getNumberOfCouple(), LocalDate.now().getMonthValue(), filesStudent));
//            }
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Не правильный запрос", HttpStatus.BAD_REQUEST);
//        }
//    }

    public FilesStudent saveFileForHomeWork(FileRequestDTO f, Students student) {
        FilesStudent filesStudent = repositoryFilesStudent.saveAndFlush(new FilesStudent(f.getName(), f.getDate(), student, "дз"));
        return filesStudent;
    }

    public ResponseEntity saveCertificate(MultipartFile file) {
        Students student = serviceStudent.getStudent();
        StudentsCourse studentsCourse = null;
        for (StudentsCourse s : student.getStudentsCourses()) {
            if (student.getGroup().getCurrentCourse() == s.getCourse()) {
                studentsCourse = s;
            }
        }
        if (!file.isEmpty()) {
            try {
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(pathSaveFile, uniqueFileName);
                file.transferTo(filePath);
                FilesStudent filesStudent = repositoryFilesStudent.saveAndFlush(new FilesStudent(uniqueFileName, LocalDate.now(), student, "Cправка"));
                List<PlanStudy> planStudies = servicePlanStudy.getPlanStudyToday(student.getGroup().getId());
                for (PlanStudy p : planStudies) {
                    repositoryOmissions.save(new Omissions(studentsCourse, LocalDate.now(), "С справкой", p.getSubjectStudy().getName(), p.getNumberOfCouple(), LocalDate.now().getMonthValue(), filesStudent));
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>("Не правильный запрос", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity getCertificate(Long id) {
        System.out.println(id);
        FilesStudent f = repositoryFilesStudent.findById(id).orElseThrow();
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(Paths.get(pathSaveFile + f.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(new CertificateFile(fileContent), HttpStatus.OK);
    }

    public record CertificateFile(byte[] file){}
}