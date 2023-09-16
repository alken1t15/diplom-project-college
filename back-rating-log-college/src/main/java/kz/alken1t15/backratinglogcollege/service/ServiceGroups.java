package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;

    public ResponseEntity<Groups> findById(Long id) {
        Groups groups = repositoryGroups.findById(id).orElse(null);
        if (groups == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
    }

    public HttpStatus save(String name) {
        Groups groups;
        if (!StringUtils.isBlank(name)) {
            groups = repositoryGroups.findByName(name).orElse(null);

            if (groups == null) {
                repositoryGroups.save(new Groups(name));
                return HttpStatus.OK;
            } else {
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    public List<Groups> findAll() {
        return repositoryGroups.findAll();
    }
}