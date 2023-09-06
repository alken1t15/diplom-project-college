package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;
}
