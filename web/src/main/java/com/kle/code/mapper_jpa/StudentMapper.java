package com.kle.code.mapper_jpa;

import com.kle.code.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface StudentMapper extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT * FROM Student student, Teach teach WHERE student.sid=teach.sid AND teach.tid= :tid",nativeQuery = true)
    List<Student> getStudentOfTeacher(@Param("tid") Integer tid);

    @Modifying
    @Query(value = "UPDATE Student SET name=:name, password=:password, updateTime=:timestamp WHERE sid=:sid")
    int updateStudent(Integer sid, String name, String password, Timestamp timestamp);

}
