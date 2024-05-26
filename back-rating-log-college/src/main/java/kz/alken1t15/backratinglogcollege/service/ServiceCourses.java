package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Courses;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryCourses;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ServiceCourses {
    private final RepositoryCourses repositoryCourses;

    public int getCountCoursesGroup(Long idGroup){
        return repositoryCourses.findByGroupId(idGroup);
    }

    public void saveNewCourses(Groups groups) {
        repositoryCourses.save(new Courses(groups,1, LocalDate.now().getYear()));
    }

}
