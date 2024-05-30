package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.*;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
import kz.alken1t15.backratinglogcollege.entity.HomeWork;
import kz.alken1t15.backratinglogcollege.entity.Teachers;
import kz.alken1t15.backratinglogcollege.service.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class ControllerTeacher {
    private final ServiceTeachers serviceTeachers;
    private final ServiceFilesGroup serviceFilesGroup;
    private final ServiceHoweWork serviceHoweWork;
    private final ServiceFilesStudent serviceFilesStudent;
    private final ServiceOmissions serviceOmissions;
    private final ServiceTaskStudents serviceTaskStudents;

    @PostMapping(path = "/home/add")
    public ResponseEntity addNewFileHomeTaskTeacher(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id) {
        return serviceHoweWork.addNewFileHomeTaskTeacher(files,id);
    }

    @PostMapping(path = "/home")
    public ResponseEntity getHomeWorkStudent(){
        Teachers teachers = serviceTeachers.getTeachers();
        List<HomeWork> homeWorks = serviceHoweWork.findByIdTeacher(teachers.getId());
        return serviceTeachers.getHomeWork(homeWorks);
    }

    @PostMapping(path = "/home/group")
    public ResponseEntity getHomeWorkStudent(@Validated @RequestBody HomeWorkGetDTO homeWorkGetDTO,BindingResult bindingResult){
        HomeWork homeWorks = serviceHoweWork.findById(homeWorkGetDTO.getIdWork());
        return serviceTeachers.getHomeWorkId(homeWorks,homeWorkGetDTO.getName(),bindingResult);
    }

    @PostMapping(path = "/work/add/ball")
    public ResponseEntity addBallForWork(@Validated @RequestBody GetHomeWorkDTO getHomeWorkDTO,BindingResult bindingResult){
        return serviceTaskStudents.addBallForWork(getHomeWorkDTO,bindingResult);
    }

    @PostMapping(path = "/work/repeat")
    public ResponseEntity addRepeatForWork(@Validated @RequestBody GetHomeWorkRepeatDTO getHomeWorkDTO, BindingResult bindingResult){
        return serviceTaskStudents.addRepeatForWork(getHomeWorkDTO,bindingResult);
    }

    @PostMapping(path = "/courses/add")
    public ResponseEntity addNewFile(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id,@NonNull @RequestParam("subjectName") String subjectName,@NonNull @RequestParam("description") String description) {
        return serviceFilesGroup.addNewFile(files,id,subjectName,description);
    }
    @PostMapping(path = "/main")
    private TeacherMainPageDTO getMainPageTeacher(@RequestBody FindGroup findGroup){
        return serviceTeachers.getMainPageTeacher(findGroup.idGroupStep,findGroup.certificateHave);
    }

    @PostMapping(path = "/file/get")
    private ResponseEntity getCertificateFile(@RequestBody CertificateFile certificateFile){
    return  serviceFilesStudent.getCertificate(certificateFile.id);
    }

    @PostMapping(path = "/omission")
    private ResponseEntity editStatusOmissionStudent(@Validated @RequestBody StatusOmissionStudent statusOmissionStudent, BindingResult bindingResult){
        return serviceOmissions.addNewOmission(statusOmissionStudent,bindingResult);
    }

    @PostMapping(path = "/work/add")
    private ResponseEntity addNewWork(@Validated @RequestBody HomeWorkAddDTO homeWorkAddDTO, BindingResult bindingResult){
        return serviceHoweWork.addNewWork(homeWorkAddDTO,bindingResult);
    }

    @PostMapping(path = "/study")
    private ResponseEntity getStudy(){
        return serviceTeachers.getStudy();
    }

    @PostMapping(path = "/study/student")
    private ResponseEntity getStudyStudent(@Validated @RequestBody StudyFindDTO studyFindDTO,BindingResult bindingResult){
        return serviceTeachers.getStudent(studyFindDTO,bindingResult);
    }

    @PostMapping(path = "/study/student/add")
    private ResponseEntity addBullStudent(@Validated @RequestBody AddBullStudentDTO addBullStudentDTO,BindingResult bindingResult){
        return serviceTeachers.addBullStudent(addBullStudentDTO,bindingResult);
    }



    public record Teacher(String firstName, String secondName, String middleName, String login, String password, String bornDate){}

    public record FindGroup(Integer idGroupStep, Boolean certificateHave){}

    public record CertificateFile(Long id){}

    public record StatusOmissionStudent(@NotNull Long idGroup,@NotNull Long idStudent,@NotNull String nameSubject,@NotNull Boolean status,@NotNull Integer numberCouple){}
}
