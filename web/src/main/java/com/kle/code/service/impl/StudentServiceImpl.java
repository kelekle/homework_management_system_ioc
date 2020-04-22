package com.kle.code.service.impl;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ypb
 * 学生服务类
 */
@Service
public class StudentServiceImpl {

    private final StudentDb studentDb;

    private final StudentHomeworkDb studentHomeworkDb;

    @Autowired
    public StudentServiceImpl(StudentDb studentDb, StudentHomeworkDb studentHomeworkDb) {
        this.studentDb = studentDb;
        this.studentHomeworkDb = studentHomeworkDb;
    }

    public Student login(String sid, String password){
        Student student = studentDb.selectStudentById(sid);
        if(student.getPassword().equals(password)){
            return student;
        }
        return null;
    }

    public Student selectStudentById(String sid){
        return studentDb.selectStudentById(sid);
    }

    public List<Map<String, String>> getStudentHomeworkOfStudent(String sid){
        return studentHomeworkDb.getStudentHomeworkOfStudent(sid);
    }

    public Map<String, String> selectStudentHomeworkById(String sid, String hid){
        return studentHomeworkDb.selectStudentHomeworkById(sid, hid);
    }

    public boolean submitHomework(String sid, String hid, String submitContent, Date date) throws SQLException {
        return studentHomeworkDb.submitHomework(sid, hid, submitContent, date);
    }

}
