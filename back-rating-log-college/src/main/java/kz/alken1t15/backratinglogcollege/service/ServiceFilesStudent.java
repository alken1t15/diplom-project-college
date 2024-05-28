package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.dto.FilesStudentDTO;
import kz.alken1t15.backratinglogcollege.dto.file.FileRequestDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FileDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FilesStudentRequestDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
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
            if (findCertificateToday()) {
                FilesStudent filesStudent = repositoryFilesStudent.findByUserIdTodayAndName(student.getId(), LocalDate.now(), "Cправка");
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(pathSaveFile, uniqueFileName);
                try {
                    file.transferTo(filePath);
                    filesStudent.setName(uniqueFileName);
                    repositoryFilesStudent.save(filesStudent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ResponseEntity(HttpStatus.OK);
            } else {
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
                    return new ResponseEntity<>("Не правильный запрос", HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            return new ResponseEntity<>("Не правильный запрос", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean findCertificateToday() {
        Students student = serviceStudent.getStudent();
        FilesStudent filesStudent = repositoryFilesStudent.findByUserIdTodayAndName(student.getId(), LocalDate.now(), "Cправка");
        return filesStudent != null;
    }


    public ResponseEntity getCertificate(Long id) {
        FilesStudent f = repositoryFilesStudent.findById(id).orElseThrow();
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(Paths.get(pathSaveFile + f.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(new CertificateFile(fileContent), HttpStatus.OK);
    }

    public FilesStudentDTO getCertificateUserToday() {
        Students student = serviceStudent.getStudent();
        FilesStudent filesStudent = repositoryFilesStudent.findByUserIdToday(student.getId(), LocalDate.now());
        if (filesStudent == null) {
            return null;
        } else {
            byte[] file = getFile(filesStudent.getId());
            return new FilesStudentDTO(filesStudent.getId(), file, filesStudent.getDateCreate(), "Справка",filesStudent.getName());
        }
    }


    public byte[] getFile(Long id) {
        FilesStudent f = repositoryFilesStudent.findById(id).orElseThrow();
        byte[] fileContent = new byte[0];
        try {
            return fileContent = Files.readAllBytes(Paths.get(pathSaveFile + f.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public record CertificateFile(byte[] file) {
    }
}