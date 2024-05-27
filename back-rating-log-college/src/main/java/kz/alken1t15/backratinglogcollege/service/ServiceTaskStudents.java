package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.GetHomeWorkDTO;
import kz.alken1t15.backratinglogcollege.dto.GetHomeWorkRepeatDTO;
import kz.alken1t15.backratinglogcollege.entity.TaskStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceTaskStudents {
    private final RepositoryTaskStudents repositoryTaskStudents;

    List<TaskStudents> findByTeacherIdAndComplete(Long id, String status){
        return repositoryTaskStudents.findByTeacherIdAndComplete(id,status);
    }

    TaskStudents findByWorkIdAndStudentIdAndComplete(Long id, Long idStudent, String status){
        return repositoryTaskStudents.findByWorkIdAndStudentIdAndComplete(id, idStudent, status);
    }

    public  TaskStudents findByIdWorkAndIdStudent(Long idHomeWord, Long idStudentId){
        return repositoryTaskStudents.findByIdWorkAndIdStudent(idHomeWord, idStudentId);
    }

    public void save(TaskStudents taskStudents){
        repositoryTaskStudents.save(taskStudents);
    }

    public ResponseEntity addBallForWork(GetHomeWorkDTO getHomeWorkDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        TaskStudents taskStudents = findByIdWorkAndIdStudent(getHomeWorkDTO.getIdWork(), getHomeWorkDTO.getIdStudent());
        taskStudents.setBall(Integer.valueOf(getHomeWorkDTO.getBall()));
        taskStudents.setStatus("Проверено");
        taskStudents.setDateBall(LocalDate.now());
        repositoryTaskStudents.save(taskStudents);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity addRepeatForWork(GetHomeWorkRepeatDTO getHomeWorkDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        TaskStudents taskStudents = findByIdWorkAndIdStudent(getHomeWorkDTO.getIdWork(), getHomeWorkDTO.getIdStudent());
        if (getHomeWorkDTO.getRepeat()) {
            taskStudents.setStatus("Не выполнено");
            repositoryTaskStudents.save(taskStudents);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
