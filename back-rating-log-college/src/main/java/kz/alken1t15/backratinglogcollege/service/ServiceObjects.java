package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.Objects;
import kz.alken1t15.backratinglogcollege.repository.RepositoryObjects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ServiceObjects {
    private final RepositoryObjects repositoryObjects;

    public ResponseEntity<Objects> findById(Long id) {
        Objects object = repositoryObjects.findById(id).orElse(null);
        if (object == null){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
