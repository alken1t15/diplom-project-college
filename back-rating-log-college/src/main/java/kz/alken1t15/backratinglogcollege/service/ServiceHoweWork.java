package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.HoweWork;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.repository.RepositoryHoweWork;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceHoweWork {
    private final RepositoryHoweWork repositoryHoweWork;
    private final ServiceStudents serviceStudent;

    public HoweWork getHomeWorkById(Long id){
        Students student = serviceStudent.getStudent();
        return  repositoryHoweWork.findByGroupAndId(student.getGroup().getId(),id);
    }
}