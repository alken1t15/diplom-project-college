package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.FileHomeTaskDTO;
import kz.alken1t15.backratinglogcollege.dto.HomeWorkRequest;
import kz.alken1t15.backratinglogcollege.dto.file.FileRequestDTO;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkDTO;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkReturnListDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.repository.RepositoryFileHomeTask;
import kz.alken1t15.backratinglogcollege.repository.RepositoryHoweWork;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudents;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudentsFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceHoweWork {
    @Autowired
    private final RepositoryHoweWork repositoryHoweWork;
    @Autowired
    private final ServiceStudents serviceStudent;
    @Autowired
    private final RepositoryTaskStudents repositoryTaskStudent;
    @Autowired
    private final ServiceFilesStudent serviceFilesStudent;
    @Autowired
    private final RepositoryTaskStudentsFiles repositoryTaskStudentsFiles;
    @Autowired
    private final RepositoryFileHomeTask repositoryFileHomeTask;
    @Value("${path.save.file}")
    private String pathSaveFile;

    public ServiceHoweWork(RepositoryHoweWork repositoryHoweWork, ServiceStudents serviceStudent, RepositoryTaskStudents repositoryTaskStudent, ServiceFilesStudent serviceFilesStudent, RepositoryTaskStudentsFiles taskStudentsFiles, RepositoryFileHomeTask repositoryFileHomeTask) {
        this.repositoryHoweWork = repositoryHoweWork;
        this.serviceStudent = serviceStudent;
        this.repositoryTaskStudent = repositoryTaskStudent;
        this.serviceFilesStudent = serviceFilesStudent;
        this.repositoryTaskStudentsFiles = taskStudentsFiles;
        this.repositoryFileHomeTask = repositoryFileHomeTask;
    }

    private final String[] arrMonth = new String[]{"янв", "фев", "мар", "апр", "май", "июн", "", "", "сен", "окт", "нояб", "дек"};

    public HomeWorkReturnDTO getAllHomeWordNotCompleted(HomeWorkRequest homeWorkRequest) {
        Students student = serviceStudent.getStudent();
        List<TaskStudents> taskStudents = getAllTaskWorkNotCompleted(student.getId());
        HomeWorkDTO howeWork = null;
        List<HomeWorkReturnListDTO> homeWorks = new ArrayList<>();
        if (taskStudents.isEmpty()) {

        } else {
            for (int i = 0; i < taskStudents.size(); i++) {
                TaskStudents taskStudent = taskStudents.get(i);
                HomeWorkDTO temp = getHomeWorkById(taskStudent.getHoweWork().getId(), taskStudent.getStatus());
                if (homeWorkRequest.getId() != null) {
                    if (temp.getId() == homeWorkRequest.getId()) {
                        howeWork = temp;
                    }
                } else {
                    if (i == 0) {
                        howeWork = temp;
                    }
                }
                homeWorks.add(new HomeWorkReturnListDTO(temp.getId(), temp.getName(), temp.getSubjectName(), temp.getStartDate(), temp.getEndDate(), temp.getTeacherName()));
            }
        }

        return new HomeWorkReturnDTO(homeWorks, howeWork);
    }

    public HomeWorkDTO getHomeWorkById(Long id, String status) {
        HomeWork howeWork = repositoryHoweWork.findByGroupAndId(id);
        String dateStart = howeWork.getStartDate().getDayOfMonth() + " " + arrMonth[howeWork.getStartDate().getMonthValue() - 1];
        String dateEnd = howeWork.getStartDate().getDayOfMonth() + " " + arrMonth[howeWork.getStartDate().getMonthValue() - 1];
        Teachers teacher = howeWork.getTeacher();
        String teacherName = String.format("%s %s %s", teacher.getSecondName(), teacher.getFirstName(), teacher.getMiddleName());
        ArrayList<FileHomeTaskDTO> fileHomeTasks = new ArrayList<>();
        for (FileHomeTask f : howeWork.getFileHomeTasks()) {
            byte[] file = new byte[0];
            try {
                file = Files.readAllBytes(Paths.get(pathSaveFile + f.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileHomeTasks.add(new FileHomeTaskDTO(f.getId(), f.getName(), f.getDateCreate(), file));
        }
        return new HomeWorkDTO(howeWork.getId(), howeWork.getName(), howeWork.getNameSubject(), dateStart, dateEnd, teacherName, howeWork.getDescription(), status, fileHomeTasks);
    }

    public List<TaskStudents> getAllTaskWorkNotCompleted(Long idStudent) {
        return repositoryTaskStudent.findByStudentAndStatus(idStudent, "Не выполнено");
    }

    public ResponseEntity addNewFileHomeTask(List<MultipartFile> files, Long id) {
        if (files.isEmpty()) {
            return new ResponseEntity<>("Нету файлов для сохранения", HttpStatus.BAD_REQUEST);
        }
        Students student = serviceStudent.getStudent();
        for (MultipartFile file : files) {
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(pathSaveFile, uniqueFileName);
            try {
                file.transferTo(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FilesStudent fileStudent = serviceFilesStudent.saveFileForHomeWork(new FileRequestDTO(uniqueFileName, LocalDate.now()), student);
            TaskStudents taskStudent = repositoryTaskStudent.findByIdWorkAndIdStudent(id, student.getId());
            taskStudent.setStatus("Сдано");
            taskStudent.setTimeCompleted(LocalDateTime.now());
            repositoryTaskStudent.save(taskStudent);
            repositoryTaskStudentsFiles.save(new TaskStudentsFiles(fileStudent, taskStudent));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity addNewFileHomeTaskTeacher(List<MultipartFile> files, Long id) {
        if (files.isEmpty()) {
            return new ResponseEntity<>("Нету файлов для сохранения", HttpStatus.BAD_REQUEST);
        } else {
            HomeWork homeWork = repositoryHoweWork.findById(id).orElseThrow();
            for (MultipartFile file : files) {
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(pathSaveFile, uniqueFileName);
                repositoryFileHomeTask.save(new FileHomeTask(uniqueFileName, LocalDate.now(), homeWork));
                try {
                    file.transferTo(filePath);
                } catch (IOException e) {
                    return new ResponseEntity<>("Нету файлов для сохранения", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}