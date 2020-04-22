package com.kle.code.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ypb
 * 教师服务类
 */
@Service
public class TeacherServiceImpl {

    private final StudentDb studentDb;

    private final HomeworkDb homeworkDb;

    private final StudentHomeworkDb studentHomeworkDb;

    private final TeacherDb teacherDb;

    @Autowired
    public TeacherServiceImpl(StudentDb studentDb, HomeworkDb homeworkDb, StudentHomeworkDb studentHomeworkDb, TeacherDb teacherDb) {
        this.studentDb = studentDb;
        this.homeworkDb = homeworkDb;
        this.studentHomeworkDb = studentHomeworkDb;
        this.teacherDb = teacherDb;
    }

    public Teacher login(String tid, String password){
        Teacher teacher = teacherDb.selectTeacherById(tid);
        if(teacher.getPassword().equals(password)){
            return teacher;
        }
        return null;
    }

    public List<Student> getStudentOfTeacher(String tid){
        return studentDb.getStudentOfTeacher(tid);
    }

    public int addTeacher(String name, String password, Date date){
        return teacherDb.addTeacher(name, password, date);
    }

    public JSONObject addHomework(String[] idList, String tid, String title, String content, Date createTime, Date updateTime) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        Homework homework = homeworkDb.addHomework(tid, title, content, createTime, updateTime);
        boolean correct = true;
        if(homework != null){
            for(String id : idList){
                if(!studentHomeworkDb.addStudentHomework(id, String.valueOf(homework.getHid()))){
                    correct = false;
                    break;
                }
            }
            if(!correct){
                jsonObject.put("status", "fail");
                jsonObject.put("msg", "布置作业失败！");
            }else {
                jsonObject.put("status", "success");
                jsonObject.put("msg","作业布置成功！");
            }
        }else {
            jsonObject.put("status", "fail");
            jsonObject.put("msg","插入异常，请重新尝试！");
        }
        return jsonObject;
    }

    public boolean addStudent(String tid, String name, String password, Date date, Date date1) {
        return studentDb.addStudent(tid, name, password, date, date1);
    }

    public JSONObject deleteHomework(String hid) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        if(homeworkDb.deleteHomework(hid) && studentHomeworkDb.deleteStudentHomeworkByHid(hid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject;
    }

    public JSONObject deleteStudent(String sid) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        if(studentDb.deleteStudent(sid) && studentHomeworkDb.deleteStudentHomeworkBySid(sid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject;
    }

    public Homework selectHomeworkById(String hid) {
        return homeworkDb.selectHomeworkById(hid);
    }

    public JSONObject updateHomework(String hid, String title, String content, Date date) {
        JSONObject jsonObject = new JSONObject();
        if(homeworkDb.updateHomework(hid, title, content, date)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "修改成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "修改失败，请重新修改！");
        }
        return jsonObject;
    }

    public boolean updateStudent(String sid, String name, String password, Date date) {
        return studentDb.updateStudent(sid, name, password, date);
    }

    public Teacher selectTeacherById(String tid) {
        return teacherDb.selectTeacherById(tid);
    }

    public List<Homework> getHomeworkOfTeacher(String tid) {
        return homeworkDb.getHomeworkOfTeacher(tid);
    }

    public List<Map<String, String>> getStudentHomeworkByHid(String hid) {
        return studentHomeworkDb.getStudentHomeworkByHid(hid);
    }

}
