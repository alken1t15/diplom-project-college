package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;

    public ResponseEntity<Groups> findById(Long id) {
        Groups groups = repositoryGroups.findById(id).orElse(null);
        if (groups == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            return new ResponseEntity<>(groups,HttpStatus.OK);
        }
    }
}
