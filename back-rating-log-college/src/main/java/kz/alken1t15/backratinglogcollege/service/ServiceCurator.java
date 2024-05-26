package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.CuratorAddDTO;
import kz.alken1t15.backratinglogcollege.entity.Curator;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.repository.RepositoryCurator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCurator {
    private final RepositoryCurator repositoryCurator;

    public Curator findById(Long id){
        return repositoryCurator.findById(id).orElse(null);
    }

    public ResponseEntity saveNewCurator(Teachers teacher) {
        repositoryCurator.save(new Curator(teacher));
        return new ResponseEntity(HttpStatus.OK);
    }
}