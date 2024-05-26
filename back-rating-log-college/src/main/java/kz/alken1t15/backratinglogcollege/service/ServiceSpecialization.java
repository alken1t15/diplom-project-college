package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Specialization;
import kz.alken1t15.backratinglogcollege.repository.RepositorySpecialization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceSpecialization {
    private final RepositorySpecialization repositorySpecialization;

    public Specialization findById(Long id){
        return repositorySpecialization.findById(id).orElse(null);
    }
}
