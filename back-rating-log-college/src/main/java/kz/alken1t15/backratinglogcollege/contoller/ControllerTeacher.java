package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherAddDTO;
import kz.alken1t15.backratinglogcollege.dto.teacher.TeacherMainPageDTO;
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

    @PostMapping(path = "/add")
    public ResponseEntity addEvaluation(@RequestBody @Validated TeacherAddDTO teacher,BindingResult bindingResult){
        return serviceTeachers.saveNewTeacher(teacher,bindingResult);
    }

    @PostMapping(path = "/home/add")
    public ResponseEntity addNewFileHomeTaskTeacher(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id) {
        return serviceHoweWork.addNewFileHomeTaskTeacher(files,id);
    }

    @PostMapping(path = "/courses/add")
    public ResponseEntity addNewFile(@Validated @RequestParam List<MultipartFile> files, @NonNull @RequestParam("id") Long id) {
        return serviceFilesGroup.addNewFile(files,id);
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



    public record Teacher(String firstName, String secondName, String middleName, String login, String password, String bornDate){}

    public record FindGroup(Integer idGroupStep, Boolean certificateHave){}

    public record CertificateFile(Long id){}

    public record StatusOmissionStudent(@NotNull Long idGroup,@NotNull Long idStudent,@NotNull String nameSubject,@NotNull Boolean status,@NotNull Integer numberCouple){}
}
