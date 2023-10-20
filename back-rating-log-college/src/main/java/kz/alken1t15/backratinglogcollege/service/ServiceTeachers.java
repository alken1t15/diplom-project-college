package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerTeacher;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ServiceTeachers {
    private final RepositoryTeachers repositoryTeachers;

    //TODO Проверка на уникальность
    public HttpStatus save(ControllerTeacher.Teacher teacher) {
        if (StringUtils.isBlank(teacher.firstName()) || StringUtils.isBlank(teacher.secondName())
                || StringUtils.isBlank(teacher.login()) || StringUtils.isBlank(teacher.password()) || StringUtils.isBlank(teacher.bornDate())){
            return HttpStatus.BAD_REQUEST;
        }
        else {
            Teachers teachers = new Teachers(teacher.firstName(),teacher.secondName(),teacher.middleName(),teacher.login(),
                    teacher.password(), LocalDate.parse(teacher.bornDate()));
            repositoryTeachers.save(teachers);
            return HttpStatus.OK;
        }
    }
}