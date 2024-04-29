package kz.alken1t15.backratinglogcollege.service;

import io.micrometer.common.util.StringUtils;
import kz.alken1t15.backratinglogcollege.contoller.ControllerGroup;
import kz.alken1t15.backratinglogcollege.dto.GroupDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTeachers;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceGroups {
    private final RepositoryGroups repositoryGroups;
    private final RepositoryStudents repositoryStudents;
    private final RepositoryTeachers repositoryTeachers;

//    public ResponseEntity<GroupDTO> findById(Long id) {
//        Groups groups = repositoryGroups.findById(id).orElse(null);
//        if (groups == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        } else {
//            return new ResponseEntity<>(returnGroup(groups), HttpStatus.OK);
//        }
//    }
//
//    public ResponseEntity save(ControllerGroup.Group group) {
//        if (group.id() == null || StringUtils.isBlank(group.name())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        else {
//           Groups groups = repositoryGroups.findByName(group.name()).orElse(null);
//            Teachers teachers = repositoryTeachers.findById(group.id()).orElse(null);
//            if (groups == null && teachers!=null) {
////                repositoryGroups.save(new Groups(group.name(),teachers));
//                return ResponseEntity.status(HttpStatus.OK).build();
//            }
//            else if (groups!= null){
//                return ResponseEntity.status(HttpStatus.CONFLICT).build();
//            }
//            else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
//        }
//    }
//
//    public List<GroupDTO> findAll() {
//        ModelMapper modelMapper = new ModelMapper();
//        return  repositoryGroups.findAll().stream().map(groups -> modelMapper.map(groups, GroupDTO.class)).toList();
//    }
//
//    public ResponseEntity addNewStudent(ControllerGroup.StudentAndGroup studentAndGroup) {
//        if (studentAndGroup.idStudent() == null || studentAndGroup.idGroup() == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        Students students = repositoryStudents.findById(studentAndGroup.idStudent()).orElse(null);
//        Groups groups = repositoryGroups.findById(studentAndGroup.idGroup()).orElse(null);
//        if (students == null || groups == null){
//            System.out.println(students);
//            System.out.println(groups);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//        else {
//            groups.getStudents().add(students);
//            students.setGroup(groups);
//            repositoryStudents.save(students);
//            repositoryGroups.save(groups);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        }
//    }
//
//    private GroupDTO returnGroup(Groups groups){
//        ModelMapper modelMapper = new ModelMapper();
//        GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
//        return groupDTO;
//    }


    public List<Groups> findByAllGroupForTeacher(Long idTeacher, LocalDate date){
        return repositoryGroups.findByAllGroupForTeacher(idTeacher,date);
    }

    public Groups findById(Long id){
        return repositoryGroups.findById(id).orElseThrow();
    }
}