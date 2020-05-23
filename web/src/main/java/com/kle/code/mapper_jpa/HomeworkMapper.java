package com.kle.code.mapper_jpa;

import com.kle.code.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface HomeworkMapper extends JpaRepository<Homework, Integer> {

    @Query(value = "SELECT * FROM Homework homework WHERE homework.tid=:tid",nativeQuery = true)
    List<Homework> getHomeworkOfTeacher(Integer tid);

    @Modifying
    @Query(value = "UPDATE Homework SET title=:title, content=:content, updateTime=:timestamp WHERE hid=:hid")
    int updateHomework(Integer hid, String title, String content, Timestamp timestamp);
}
