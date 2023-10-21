package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.entity.Objects;
import kz.alken1t15.backratinglogcollege.repository.RepositoryObjects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServiceObjects {
    private final RepositoryObjects repositoryObjects;

    public ResponseEntity<Objects> findById(Long id) {
        Objects object = repositoryObjects.findById(id).orElse(null);
        if (object == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public ResponseEntity save(String name) {
        if (StringUtils.isBlank(name)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Objects objects = repositoryObjects.findByName(name).orElse(null);
        if (objects == null){
           repositoryObjects.save(new Objects(name));
           return ResponseEntity.status(HttpStatus.OK).build();
        }else {
          return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<List<Objects>> getAllObject() {
        return ResponseEntity.status(HttpStatus.OK).body(repositoryObjects.findAll());
    }
}