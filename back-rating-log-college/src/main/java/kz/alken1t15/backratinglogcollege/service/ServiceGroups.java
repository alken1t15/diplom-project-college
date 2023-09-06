package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;
}
