package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.StudentAddDTO;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.StudentsCourse;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryGroups;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudentCourse;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudents;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import static org.apache.poi.util.LocaleID.DA;

@AllArgsConstructor
@Service
public class ServiceStudents {
    private final RepositoryStudents repositoryStudents;
    private final RepositoryGroups repositoryGroups;
    private final ServiceUsers serviceUser;
    private final RepositoryStudentCourse repositoryStudentCourse;
    private final PasswordEncoder passwordEncoder;
    @Value("${path.save.file}")
    private String pathSaveFile;

    @Autowired
    public ServiceStudents(RepositoryStudents repositoryStudents, RepositoryGroups repositoryGroups, ServiceUsers serviceUser, RepositoryStudentCourse repositoryStudentCourse, PasswordEncoder passwordEncoder) {
        this.repositoryStudents = repositoryStudents;
        this.repositoryGroups = repositoryGroups;
        this.serviceUser = serviceUser;
        this.repositoryStudentCourse = repositoryStudentCourse;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Students> findById(Long id) {
        Students students = repositoryStudents.findById(id).orElse(null);
        if (students == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
    }

    public Students getStudent() {
        User user = serviceUser.getUser();
        return repositoryStudents.findById(user.getId()).orElseThrow();
    }


    public Students findByIdStudent(Long id) {
        return repositoryStudents.findByIdCustom(id).orElseThrow();
    }

    public ResponseEntity saveNewStudent(StudentAddDTO student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String nameError = fieldError.getDefaultMessage();
                errors.add(String.format("Поле %s ошибка: %s", field, nameError));
            }
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        Groups group = repositoryGroups.findById(student.getGroupId()).orElse(null);
        if (group == null) {
            return new ResponseEntity("Такой группы нету", HttpStatus.BAD_REQUEST);
        }
        Long id = serviceUser.save(student.getLogin(), passwordEncoder.encode(student.getPassword()), "student");
        Students students = repositoryStudents.save(new Students(id, group, student.getFirstName(), student.getSecondName(), student.getMiddleName(), student.getBornDate(), student.getSubgroupName()));
        repositoryStudentCourse.save(new StudentsCourse(students, 1));
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity saveNewStudentExcel(List<MultipartFile> multipartFile, Long id) {
        String firstName = null;
        String secondName= null;
        String middleName= null;
        LocalDate born= null;
        String login= null;
        String password= null;
        for (MultipartFile file : multipartFile) {
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(pathSaveFile, uniqueFileName);
            try {
                file.transferTo(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String excelFilePath = pathSaveFile + uniqueFileName;

            try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = sheet.iterator();


                for (int i = 0; i < 6; i++) {
                    Row row = iterator.next();
                    Cell cell = row.getCell(1);
                    String temp = null;
                    Date date = null;
                    switch (cell.getCellType()) {
                        case STRING:
                            temp = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            date = cell.getDateCellValue();
                            break;
                        default:
                            System.out.print("Unknown value\t");
                            break;
                    }
                    if (i==0){
                        firstName = temp;
                    }
                    else if (i==1){
                        secondName = temp;
                    }
                    else if (i==2){
                        middleName = temp;
                    }
                    else if (i==3){
                        Instant instant = date.toInstant();
                        born = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                    else if (i==4){
                        login = temp;
                    }
                    else if (i==5){
                        password = temp;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveNewStudent(new StudentAddDTO(id,firstName,secondName,middleName,login,password,born,null));
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    public void saveNewStudent(StudentAddDTO student) {
        Groups group = repositoryGroups.findById(student.getGroupId()).orElse(null);
        Long id = serviceUser.save(student.getLogin(), passwordEncoder.encode(student.getPassword()), "student");
        Students students = repositoryStudents.save(new Students(id, group, student.getFirstName(), student.getSecondName(), student.getMiddleName(), student.getBornDate(), student.getSubgroupName()));
        repositoryStudentCourse.save(new StudentsCourse(students, 1));
    }
}