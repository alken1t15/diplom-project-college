package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.repository.RepositoryCourses;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceCourses {
    private final RepositoryCourses repositoryCourses;

    public int getCountCoursesGroup(Long idGroup){
        return repositoryCourses.findByGroupId(idGroup);
    }
}
