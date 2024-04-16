package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.FileHomeTaskDTO;
import kz.alken1t15.backratinglogcollege.dto.HomeWorkRequest;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkDTO;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkReturnDTO;
import kz.alken1t15.backratinglogcollege.dto.work.HomeWorkReturnListDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.repository.RepositoryHoweWork;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ServiceHoweWork(RepositoryHoweWork repositoryHoweWork, ServiceStudents serviceStudent, RepositoryTaskStudents repositoryTaskStudent) {
        this.repositoryHoweWork = repositoryHoweWork;
        this.serviceStudent = serviceStudent;
        this.repositoryTaskStudent = repositoryTaskStudent;
    }

    private final String[] arrMonth = new String[]{"янв", "фев", "мар", "апр", "май", "июн", "", "", "сен", "окт", "нояб", "дек"};

    public HomeWorkReturnDTO getAllHomeWordNotCompleted(HomeWorkRequest homeWorkRequest) {
        Students student = serviceStudent.getStudent();
        List<TaskStudents> taskStudents = getAllTaskWorkNotCompleted(student.getId());
        HomeWorkDTO howeWork = null;
        List<HomeWorkReturnListDTO> homeWorks = new ArrayList<>();
        if (taskStudents.isEmpty()) {

        } else {
            for (int i = 0;i<taskStudents.size();i++){
                TaskStudents taskStudent = taskStudents.get(i);
                HomeWorkDTO temp = getHomeWorkById(taskStudent.getHoweWork().getId(),taskStudent.getStatus());
                if (homeWorkRequest.getId()!= null){
                    if (temp.getId()==homeWorkRequest.getId()) {
                        howeWork = temp;
                    }
                }else {
                    if (i == 0) {
                        howeWork = temp;
                    }
                }
                homeWorks.add(new HomeWorkReturnListDTO(temp.getId(), temp.getName(),temp.getSubjectName(),temp.getStartDate(),temp.getEndDate(),temp.getTeacherName()));
            }
        }

        return new HomeWorkReturnDTO(homeWorks,howeWork);
    }

    public HomeWorkDTO getHomeWorkById(Long id, String status) {
        HomeWork howeWork = repositoryHoweWork.findByGroupAndId(id);
        String dateStart = howeWork.getStartDate().getDayOfMonth() + " " + arrMonth[howeWork.getStartDate().getMonthValue() - 1];
        String dateEnd = howeWork.getStartDate().getDayOfMonth() + " " + arrMonth[howeWork.getStartDate().getMonthValue() - 1];
        Teachers teacher = howeWork.getTeacher();
        String teacherName = String.format("%s %s %s", teacher.getSecondName(), teacher.getFirstName(), teacher.getMiddleName());
        ArrayList<FileHomeTaskDTO> fileHomeTasks = new ArrayList<>();
        for (FileHomeTask f : howeWork.getFileHomeTasks()){
            fileHomeTasks.add(new FileHomeTaskDTO(f.getId(),f.getName(),f.getDateCreate()));
        }
        return new HomeWorkDTO(howeWork.getId(),howeWork.getName(), howeWork.getNameSubject(), dateStart, dateEnd, teacherName, howeWork.getDescription(), status,fileHomeTasks);
    }

    public List<TaskStudents> getAllTaskWorkNotCompleted(Long idStudent) {
        return repositoryTaskStudent.findByStudentAndStatus(idStudent, "Не выполнено");
    }
}