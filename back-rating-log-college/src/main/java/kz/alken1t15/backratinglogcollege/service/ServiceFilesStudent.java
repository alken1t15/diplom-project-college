package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.dto.work.FilesStudentRequestDTO;
import kz.alken1t15.backratinglogcollege.entity.FilesStudent;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFilesStudent;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ServiceFilesStudent {
    private final RepositoryFilesStudent repositoryFilesStudent;
    private final ServiceStudents serviceStudent;


    public HttpStatus save(FilesStudentRequestDTO file) {
        Students student = serviceStudent.getStudent();
        if (!StringUtils.isBlank(file.getFile()) && !StringUtils.isBlank(file.getName())) {
            repositoryFilesStudent.save(new FilesStudent(file.getName(), file.getFile(), LocalDate.now(), student));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}