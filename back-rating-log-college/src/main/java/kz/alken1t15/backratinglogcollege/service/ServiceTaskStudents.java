package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.TaskStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
