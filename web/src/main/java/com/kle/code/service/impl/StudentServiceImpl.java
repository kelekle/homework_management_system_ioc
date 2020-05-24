package com.kle.code.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.kle.code.dao.StudentHomeworkMapper;
import com.kle.code.dao.StudentMapper;
import com.kle.code.model.Student;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ypb
 * 学生服务类
 */
@Service
public class StudentServiceImpl {

    private final StudentMapper studentMapper;
    private final StudentHomeworkMapper studentHomeworkMapper;

    public StudentServiceImpl(StudentMapper studentMapper, StudentHomeworkMapper studentHomeworkMapper) {
        this.studentMapper = studentMapper;
        this.studentHomeworkMapper = studentHomeworkMapper;
    }

    public boolean login(String sid, String password){
        Student student = studentMapper.selectByPrimaryKey(Integer.valueOf(sid));
        if(student.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    public Student selectStudentById(String sid){
        return studentMapper.selectByPrimaryKey(Integer.valueOf(sid));
    }

    public List<Map<String, String>> getStudentHomeworkOfStudent(String sid){
        List<Map<String, String>> res = new ArrayList<>();
        List<Map<String, Object>> mapList = studentHomeworkMapper.selectStudentHomeworkOfStudent(Integer.valueOf(sid));
        for (Map<String, Object> map : mapList){
            Map<String, String> map1 = new HashMap<>();
            map1.put("hid", String.valueOf(map.get("hid")));
            map1.put("title", String.valueOf(map.get("title")));
            map1.put("content", String.valueOf(map.get("content")));
            map1.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("create_time")));
            map1.put("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("update_time")));
            if(map.get("submit_time") != null){
                map1.put("submit_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("submit_time")));
                map1.put("submit_content", String.valueOf(map.get("submit_content")));
            }else {
                map1.put("submit_time", "未提交");
                map1.put("submit_content", "未提交");
            }
            res.add(map1);
        }
        return res;
    }

    public Map<String, String> selectStudentHomeworkById(String sid, String hid){
        Map<String, String> res = new HashMap<>();
        Map<String, Object> map = studentHomeworkMapper.selectStudentHomeworkByPrimaryKey(Integer.valueOf(sid), Integer.valueOf(hid));
        res.put("sid", sid);
        res.put("hid", hid);
        res.put("title", String.valueOf(map.get("title")));
        res.put("content", String.valueOf(map.get("content")));
        res.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("create_time")));
        res.put("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("update_time")));
        if(map.get("submit_time") != null){
            res.put("submit_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("submit_time")));
            res.put("submit_content", String.valueOf(map.get("submit_content")));
        }else {
            res.put("submit_time", "未提交");
            res.put("submit_content", "未提交");
        }
        return res;
    }

    public boolean submitHomework(String sid, String hid, String submitContent, Date date) throws SQLException {
        return studentHomeworkMapper.submitHomework(Integer.valueOf(sid), Integer.valueOf(hid), submitContent, new Timestamp(date.getTime())) > 0;
    }

}
