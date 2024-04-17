package kz.alken1t15.backratinglogcollege.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_students_files")
@Getter
@Setter
@NoArgsConstructor
public class TaskStudentsFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_file_students")
    private FilesStudent filesStudent;

    @ManyToOne
    @JoinColumn(name = "id_task_students")
    private TaskStudents taskStudent;

    public TaskStudentsFiles(FilesStudent filesStudent, TaskStudents taskStudent) {
        this.filesStudent = filesStudent;
        this.taskStudent = taskStudent;
    }
}