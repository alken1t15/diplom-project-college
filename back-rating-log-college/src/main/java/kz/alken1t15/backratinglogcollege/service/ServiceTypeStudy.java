package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.TypeStudyAddDTO;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTypeStudy;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceTypeStudy {
    private final RepositoryTypeStudy repositoryTypeStudy;
    private final ServiceStudyProcess serviceStudyProcess;


    public ResponseEntity saveNewTypeStudy(TypeStudyAddDTO typeStudy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        StudyProcess studyProcess = serviceStudyProcess.findById(typeStudy.getIdStudyProcess());
        if (studyProcess==null){
            return new ResponseEntity("Такого семестра нету",HttpStatus.BAD_REQUEST);
        }
        repositoryTypeStudy.save(new TypeStudy(typeStudy.getName(),typeStudy.getStart(),typeStudy.getEnd(),studyProcess));
        return new ResponseEntity(HttpStatus.OK);
    }

    public TypeStudy findById(Long id){
        return repositoryTypeStudy.findById(id).orElse(null);
    }
}
