package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceTeachers {
    private final RepositoryTeachers repositoryTeachers;
}
