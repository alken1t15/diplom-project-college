package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.AuditoriumAddDTO;
import kz.alken1t15.backratinglogcollege.entity.study.Auditorium;
import kz.alken1t15.backratinglogcollege.repository.RepositoryAuditorium;
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
public class ServiceAuditorium {
    private final RepositoryAuditorium repositoryAuditorium;


    public ResponseEntity saveNewAuditorium(AuditoriumAddDTO auditorium, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Auditorium auditorium1 = repositoryAuditorium.findByBlockAndFloorAndCabinet(auditorium.getBlock(),auditorium.getFlour(),auditorium.getCabinet());
        if (auditorium1!=null){
            return new ResponseEntity("Такая аудитория уже есть",HttpStatus.CONFLICT);
        }
        repositoryAuditorium.save(new Auditorium(auditorium.getBlock(),auditorium.getFlour(),auditorium.getCabinet()));
        return new ResponseEntity(HttpStatus.OK);
    }
}
