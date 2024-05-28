package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.dto.TotalEvalDTO;
import kz.alken1t15.backratinglogcollege.entity.*;
import kz.alken1t15.backratinglogcollege.entity.study.process.StudyProcess;
import kz.alken1t15.backratinglogcollege.entity.study.process.TypeStudy;
import kz.alken1t15.backratinglogcollege.repository.RepositoryEvaluations;
import kz.alken1t15.backratinglogcollege.repository.RepositoryStudyProcess;
import kz.alken1t15.backratinglogcollege.repository.RepositoryTaskStudents;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ServiceTotal {
    private final ServiceStudents serviceStudents;
    private final RepositoryStudyProcess repositoryStudyProcess;
    private final RepositoryEvaluations repositoryEvaluations;
    private final RepositoryTaskStudents repositoryTaskStudents;

    public ResponseEntity getTotalEvaluations() {
        int[] arrMonth = new int[]{9, 10, 11, 12, 1, 2, 3, 4, 5, 6};
        Students students = serviceStudents.getStudent();
        Groups groups = students.getGroup();
        List<Courses> coursess = groups.getCourses();
        Long id = students.getGroup().getId();
        List<TotalEvalDTO> totalEvalDTOS = new ArrayList<>();
        for (Courses c : coursess) {
            List<StudyProcess> studyProcesses = repositoryStudyProcess.findByCourseGroup(c.getCourse(), id);
            for (StudyProcess studyProcess : studyProcesses) {
                List<String> nameSubject = repositoryEvaluations.findByDateStudentCourseDistinctName(studyProcess.getDateStart(), studyProcess.getDateEnd(), students.getId());
                for (String name : nameSubject) {
                    int total = 0;
                    int count = 0;
                    List<Evaluations> evaluations = repositoryEvaluations.findByDateStudentCourseSubjectName(studyProcess.getDateStart(), studyProcess.getDateEnd(), students.getId(), name);
                    for (Evaluations e : evaluations) {
                        total += e.getBall();
                    }
                    count+=evaluations.size();
                    List<TaskStudents> taskStudents = repositoryTaskStudents.findByIdStudentAndDate(students.getId(),studyProcess.getDateStart(), studyProcess.getDateEnd(),name);
                    for (TaskStudents e : taskStudents) {
                        total += e.getBall();
                    }
                    count+=taskStudents.size();
                    int totalEval = total/count;
                    totalEvalDTOS.add(new TotalEvalDTO(name, totalEval, c.getCourse(),studyProcess.getSemester()));
                }
            }
        }

        Map<Integer, Map<Integer, Map<String, Integer>>> courses = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            courses.put(i, new HashMap<>());
            courses.get(i).put(1, new HashMap<>());
            courses.get(i).put(2, new HashMap<>());
        }

        for (TotalEvalDTO item : totalEvalDTOS) {
            int course = item.getCourse();
            int semester = item.getSemester();
            String name = item.getName();
            int total = item.getTotal();

            courses.get(course).get(semester).put(name, total);
        }

        Set<String> subjectNames = new HashSet<>();
        for (TotalEvalDTO item : totalEvalDTOS) {
            subjectNames.add(item.getName());
        }
        List<String> subjectList = new ArrayList<>(subjectNames);


        int numRows = subjectList.size() + 2;
        int numCols = 1 + 4 * 2;
        String[][] resultArray = new String[numRows][numCols];

        resultArray[0][0] = "Курс";
        for (int course = 1; course <= 4; course++) {
            resultArray[0][(course - 1) * 2 + 1] = course + " курс";
            resultArray[0][(course - 1) * 2 + 2] = "";
        }

        resultArray[1][0] = "Предметы\\Четверть";
        for (int course = 1; course <= 4; course++) {
            resultArray[1][(course - 1) * 2 + 1] = "1";
            resultArray[1][(course - 1) * 2 + 2] = "2";
        }

        for (int i = 0; i < subjectList.size(); i++) {
            String name = subjectList.get(i);
            resultArray[i + 2][0] = name;
            for (int course = 1; course <= 4; course++) {
                for (int semester = 1; semester <= 2; semester++) {
                    Map<String, Integer> semesterData = courses.get(course).get(semester);
                    if (semesterData.containsKey(name)) {
                        resultArray[i + 2][(course - 1) * 2 + semester] = String.valueOf(semesterData.get(name));
                    } else {
                        resultArray[i + 2][(course - 1) * 2 + semester] = "-";
                    }
                }
            }
        }

        return new ResponseEntity(resultArray, HttpStatus.OK);
    }
}
