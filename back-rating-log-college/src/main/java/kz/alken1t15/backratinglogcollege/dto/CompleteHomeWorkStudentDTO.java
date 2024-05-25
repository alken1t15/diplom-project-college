package kz.alken1t15.backratinglogcollege.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteHomeWorkStudentDTO {
    private String nameWork;
    private String startWork;
    private String endWork;
    private String nameTeacher;
    private String nameSubject;
    private String description;
    private String nameStudent;
    private String completeDate;
    private List<byte[]> fileWork;
    private List<byte[]> fileStudents;
    List<CompleteHomeWorkDTO> completeHomeWork;

    public CompleteHomeWorkStudentDTO(String nameWork, String startWork, String endWork, String nameTeacher, String nameSubject, String description, String nameStudent, String completeDate) {
        this.nameWork = nameWork;
        this.startWork = startWork;
        this.endWork = endWork;
        this.nameTeacher = nameTeacher;
        this.nameSubject = nameSubject;
        this.description = description;
        this.nameStudent = nameStudent;
        this.completeDate = completeDate;
    }
}