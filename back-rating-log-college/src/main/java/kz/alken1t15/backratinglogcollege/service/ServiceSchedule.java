package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.repository.RepositorySchedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceSchedule {
    private final RepositorySchedule repositorySchedule;
}
