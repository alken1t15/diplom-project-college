package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.SubjectAddDTO;
import kz.alken1t15.backratinglogcollege.dto.SubjectStudyDTO;
import kz.alken1t15.backratinglogcollege.entity.study.SubjectStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositorySubject;
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
public class ServiceSubject {
    private final RepositorySubject repositorySubject;


    public ResponseEntity saveNewSubject(SubjectAddDTO subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        SubjectStudy subjectStudy = repositorySubject.findByName(subject.getName());
        if (subjectStudy!=null){
            return new ResponseEntity("Такой предмет уже есть",HttpStatus.CONFLICT);
        }
        repositorySubject.save(new SubjectStudy(subject.getName()));
        return new ResponseEntity(HttpStatus.OK);
    }

    public SubjectStudy findById(Long id){
        return repositorySubject.findById(id).orElse(null);
    }

    public ResponseEntity findAll() {
        List<SubjectStudy> subjectStudies = repositorySubject.findAll();
        List<SubjectStudyDTO> subjectStudyDTOS = new ArrayList<>();
        for (SubjectStudy subjectStudy : subjectStudies){
            subjectStudyDTOS.add(new SubjectStudyDTO(subjectStudy.getId(),subjectStudy.getName()));
        }
        return new ResponseEntity(subjectStudyDTOS,HttpStatus.OK);
    }
}
