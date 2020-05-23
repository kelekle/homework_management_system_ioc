package com.kle.code.mapper_jpa;

import com.kle.code.model.StudentHomework;
import com.kle.code.model.pk.StudentHomeworkPK;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author ypb
 */
@Transactional
public interface StudentHomeworkMapper extends JpaRepository<StudentHomework, StudentHomeworkPK> {

    @Query(value = "SELECT homework.hid, homework.title, homework.content, homework.createTime, " +
            "homework.updateTime, student_homework.submitContent, student_homework.submitTime " +
            "FROM StudentHomework student_homework, Homework homework " +
            "WHERE homework.hid=student_homework.studentHomeworkPK.hid AND student_homework.studentHomeworkPK.sid=:sid")
    List<Object[]> getStudentHomeworkOfStudent(Integer sid);

    @Query(value = "SELECT homework.title, homework.content, homework.createTime, homework.updateTime, " +
            "student_homework.submitContent, student_homework.submitTime " +
            "FROM StudentHomework student_homework, Homework homework " +
            "WHERE student_homework.studentHomeworkPK.sid=:sid AND student_homework.studentHomeworkPK.hid=:hid " +
            "AND homework.hid=:hid")
    Object selectStudentHomeworkById(Integer sid, Integer hid);

    @Modifying
    @Query(value = "UPDATE StudentHomework sh SET sh.submitContent=:submitContent, sh.submitTime=:date " +
            "WHERE sh.studentHomeworkPK.sid=:sid AND sh.studentHomeworkPK.hid=:hid")
    int submitHomework(Integer sid, Integer hid, String submitContent, Timestamp date);

    @Modifying
    @Query(value = "DELETE FROM StudentHomework sh WHERE sh.studentHomeworkPK.sid=:sid")
    int deleteStudentHomeworkBySid(Integer sid);

    @Modifying
    @Query(value = "DELETE FROM StudentHomework sh WHERE sh.studentHomeworkPK.hid=:hid")
    int deleteStudentHomeworkByHid(Integer hid);

    @Query(value = "SELECT student_homework.studentHomeworkPK.sid, homework.title, homework.content, " +
            "homework.createTime, homework.updateTime, student_homework.submitContent, student_homework.submitTime " +
            "FROM Homework homework, StudentHomework student_homework " +
            "WHERE homework.hid=:hid AND student_homework.studentHomeworkPK.hid=:hid")
    List<Object[]> getStudentHomeworkByHid(Integer hid);

}
