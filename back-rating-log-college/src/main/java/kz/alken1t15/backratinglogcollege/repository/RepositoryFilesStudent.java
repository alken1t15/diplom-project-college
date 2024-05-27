package kz.alken1t15.backratinglogcollege.repository;

import jakarta.persistence.Id;
import kz.alken1t15.backratinglogcollege.entity.FilesStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface RepositoryFilesStudent extends JpaRepository<FilesStudent,Long> {

    @Query("select f from FilesStudent f where f.dateCreate = ?1 and  f.student.id = ?2")
    FilesStudent findByDateCreateAndIdStudents(LocalDate date,Long idStudent);

    @Query("select  f from  FilesStudent f where f.student.id = ?1 and  f.dateCreate = ?2 and  f.typeFile='Cправка'")
    FilesStudent findByUserIdToday(Long id,LocalDate date);

    @Query("select f from FilesStudent f where f.student.id = ?1 and f.dateCreate = ?2 and  f.typeFile=?3")
    FilesStudent findByUserIdTodayAndName(Long id,LocalDate date,String name);
}