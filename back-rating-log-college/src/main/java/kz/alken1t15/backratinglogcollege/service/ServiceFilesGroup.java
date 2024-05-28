package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.work.CourseDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FileDTO;
import kz.alken1t15.backratinglogcollege.dto.work.FilesReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.GetFileForCourseAndIdFileDTO;
import kz.alken1t15.backratinglogcollege.entity.Courses;
import kz.alken1t15.backratinglogcollege.entity.Groups;
import kz.alken1t15.backratinglogcollege.entity.Students;
import kz.alken1t15.backratinglogcollege.entity.study.FilesGroup;
import kz.alken1t15.backratinglogcollege.repository.RepositoryCourses;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFilesGroup;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceFilesGroup {
    @Autowired
    private final RepositoryFilesGroup repositoryFilesGroup;
    @Autowired
    private final ServiceStudents serviceStudents;
    @Autowired
    private final RepositoryCourses repositoryCourses;

    public ServiceFilesGroup(RepositoryFilesGroup repositoryFilesGroup, ServiceStudents serviceStudents, RepositoryCourses repositoryCourses) {
        this.repositoryFilesGroup = repositoryFilesGroup;
        this.serviceStudents = serviceStudents;
        this.repositoryCourses = repositoryCourses;
    }

    @Value("${path.save.file}")
    private String pathSaveFile;


    public FilesReturnDTO getFiles(GetFileForCourseAndIdFileDTO file) {
        FilesReturnDTO filesReturnDTO = new FilesReturnDTO();
        Students student = serviceStudents.getStudent();
        List<CourseDTO> courses = new ArrayList<>();

        //Получаем конкретную группу
        //Получение конкретный курс, и год и также получение всех курсов учебы
        Groups group = student.getGroup();
        for (Courses c : group.getCourses()) {
            if (file.getCourse() != null) {
                if (c.getCourse() == file.getCourse()) {
                    filesReturnDTO.setCurrentCourse(c.getCourse());
                    filesReturnDTO.setCurrentYear(c.getYear());
                }
            } else {
                if (c.getCourse() == group.getCurrentCourse()) {
                    filesReturnDTO.setCurrentCourse(c.getCourse());
                    filesReturnDTO.setCurrentYear(c.getYear());
                }
            }
            courses.add(new CourseDTO(c.getCourse(), c.getYear()));
        }
        filesReturnDTO.setCourses(courses);

        //Получение файлов группы
        List<FilesGroup> filesGroups;
        if (file.getCourse() != null) {
            filesGroups = repositoryFilesGroup.findByCourseAndIdGroup(file.getCourse(), student.getGroup().getId());
        } else {
            filesGroups = repositoryFilesGroup.findByCourseAndIdGroup(student.getGroup().getCurrentCourse(), student.getGroup().getId());
        }

        List<FileDTO> files = new ArrayList<>();
        for (FilesGroup f : filesGroups) {
            String str = f.getDateCreate().getDayOfMonth() + " " + getMonth(f.getDateCreate().getMonthValue());
            byte[] fileContent = new byte[0];
            try {
                fileContent = Files.readAllBytes(Paths.get(pathSaveFile + f.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            files.add(new FileDTO(f.getId(), f.getName(), str, fileContent));
        }
        filesReturnDTO.setFiles(files);
        return filesReturnDTO;
    }

    public String getMonth(int idMonth) {
        idMonth--;
        String[] arrMonth = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня", "", "", "сентября", "октября", "ноября", "декабря"};
        return arrMonth[idMonth];
    }

    public ResponseEntity addNewFile(List<MultipartFile> files, @NonNull Long id) {
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(pathSaveFile, uniqueFileName);
                try {
                    file.transferTo(filePath);
                    Courses courses = repositoryCourses.findById(id).orElseThrow();
                    repositoryFilesGroup.save(new FilesGroup(uniqueFileName, LocalDate.now(), courses));
                    return new ResponseEntity(HttpStatus.OK);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}