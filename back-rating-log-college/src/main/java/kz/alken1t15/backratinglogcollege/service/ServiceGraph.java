package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Graph;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGraph;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceGraph {
    private RepositoryGraph repositoryGraph;
}