package com.kle.code.service.impl;

import com.kle.code.mapper_jpa.StudentHomeworkMapper;
import com.kle.code.mapper_jpa.StudentMapper;
import com.kle.code.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author ypb
 * 学生服务类
 */
@Service
public class StudentServiceImpl {

//    private final StudentDb studentDb;
    private final StudentMapper studentDb;

    private final StudentHomeworkMapper studentHomeworkDb;

    @Autowired
    public StudentServiceImpl(StudentMapper studentDb, StudentHomeworkMapper studentHomeworkDb) {
        this.studentDb = studentDb;
        this.studentHomeworkDb = studentHomeworkDb;
    }

    public Student login(String sid, String password){
        Student student = studentDb.findById(Integer.valueOf(sid)).get();
        if(student.getPassword().equals(password)){
            return student;
        }
        return null;
    }

    public Student selectStudentById(String sid){
        return studentDb.findById(Integer.valueOf(sid)).get();
//        return studentDb.selectStudentById(sid);
    }

    public List<Map<String, String>> getStudentHomeworkOfStudent(String sid){
        List<Object[]> lists = studentHomeworkDb.getStudentHomeworkOfStudent(Integer.valueOf(sid));
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (Object[] objects : lists){
            Map<String, String> map = new HashMap<>();
            map.put("hid", objects[0].toString());
            map.put("title", objects[1].toString());
            map.put("content", objects[2].toString());
            map.put("create_time", objects[3].toString());
            map.put("update_time", objects[4].toString());
            map.put("submit_content", objects[5].toString());
            map.put("submit_time", objects[6].toString());
            mapList.add(map);
        }
        return mapList;
    }

    public Map<String, String> selectStudentHomeworkById(String sid, String hid){
        Map<String, String> map = new HashMap<>();
        Object[] objects = (Object[]) studentHomeworkDb.selectStudentHomeworkById(Integer.valueOf(sid), Integer.valueOf(hid));
        map.put("sid", sid);
        map.put("hid", hid);
        map.put("title", objects[0].toString());
        map.put("content", objects[1].toString());
        map.put("create_time", objects[2].toString());
        map.put("update_time", objects[3].toString());
        map.put("submit_content", objects[4].toString());
        map.put("submit_time", objects[5].toString());
        return map;
    }

    public boolean submitHomework(String sid, String hid, String submitContent, Date date) throws SQLException {
        return studentHomeworkDb.submitHomework(Integer.valueOf(sid), Integer.valueOf(hid), submitContent, new Timestamp(date.getTime())) > 0;
    }

    public Object test(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return studentDb.findAll(pageRequest);
    }

}
