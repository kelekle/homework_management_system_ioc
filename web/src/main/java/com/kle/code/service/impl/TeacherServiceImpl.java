package com.kle.code.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.mapper_jpa.*;
import com.kle.code.model.*;
import com.kle.code.model.pk.StudentHomeworkPK;
import com.kle.code.model.pk.TeachPK;
import com.kle.code.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author ypb
 * 教师服务类
 */
@Service
public class TeacherServiceImpl {

    private final StudentMapper studentDb;

    private final TeachMapper teachMapper;

    private final HomeworkMapper homeworkDb;

    private final StudentHomeworkMapper studentHomeworkDb;

    private final TeacherMapper teacherDb;

    @Autowired
    public TeacherServiceImpl(StudentMapper studentDb, TeachMapper teachMapper, HomeworkMapper homeworkDb,
                              StudentHomeworkMapper studentHomeworkDb, TeacherMapper teacherDb) {
        this.studentDb = studentDb;
        this.teachMapper = teachMapper;
        this.homeworkDb = homeworkDb;
        this.studentHomeworkDb = studentHomeworkDb;
        this.teacherDb = teacherDb;
    }

    public Teacher login(String tid, String password){
        Teacher teacher = teacherDb.findById(Integer.valueOf(tid)).get();
        if(teacher.getPassword().equals(password)){
            return teacher;
        }
        return null;
    }

    public List<Student> getStudentOfTeacher(String tid){
        return studentDb.getStudentOfTeacher(Integer.valueOf(tid));
    }

    public int addTeacher(String name, String password, Date date){
        Teacher teacher = SpringContextUtil.getBean("teacher");
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        return teacherDb.save(teacher).getTid();
    }

    public JSONObject addHomework(String[] idList, String tid, String title, String content, Date createTime, Date updateTime) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        Homework homework = SpringContextUtil.getBean("homework");
        homework.setTid(Integer.parseInt(tid));
        homework.setTitle(title);
        homework.setContent(content);
        homework.setCreateTime(createTime);
        homework.setUpdateTime(updateTime);
        int hid = homeworkDb.save(homework).getHid();
        boolean correct = true;
        if(homework != null){
            for(String id : idList){
                StudentHomework studentHomework = SpringContextUtil.getBean("student_homework");
                StudentHomeworkPK studentHomeworkPK = new StudentHomeworkPK();
                studentHomeworkPK.setSid(Integer.parseInt(id));
                studentHomeworkPK.setHid(hid);
                studentHomework.setStudentHomeworkPK(studentHomeworkPK);
                studentHomeworkDb.save(studentHomework);
            }
            jsonObject.put("status", "success");
            jsonObject.put("msg","作业布置成功！");
        }else {
            jsonObject.put("status", "fail");
            jsonObject.put("msg","插入异常，请重新尝试！");
        }
        return jsonObject;
    }

    public boolean addStudent(String tid, String name, String password, Date date, Date date1) {
        Student student = (Student) SpringContextUtil.getApplicationContext().getBean("student");
        student.setName(name);
        student.setPassword(password);
        student.setCreateTime(date);
        student.setUpdateTime(date1);
        int sid = studentDb.save(student).getSid();
        Teach teach = (Teach) SpringContextUtil.getApplicationContext().getBean("teach");
        TeachPK teachPK = new TeachPK();
        teachPK.setSid(sid);
        teachPK.setTid(Integer.valueOf(tid));
        teach.setTeachPK(teachPK);
        teach.setCreateTime(date);
        return teachMapper.save(teach).getTeachPK().getSid() > 0;
//        return studentDb.save(tid, name, password, date, date1);
    }

    public JSONObject deleteHomework(String hid) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        homeworkDb.deleteById(Integer.valueOf(hid));
        if(studentHomeworkDb.deleteStudentHomeworkByHid(Integer.valueOf(hid)) > 0){
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
        studentDb.deleteById(Integer.valueOf(sid));
        if(studentHomeworkDb.deleteStudentHomeworkBySid(Integer.valueOf(sid)) > 0){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject;
    }

    public Homework selectHomeworkById(String hid) {
        return homeworkDb.findById(Integer.valueOf(hid)).get();
    }

    public JSONObject updateHomework(String hid, String title, String content, Date date) {
        JSONObject jsonObject = new JSONObject();
        if(homeworkDb.updateHomework(Integer.valueOf(hid), title, content, new Timestamp(date.getTime())) > 0){
            jsonObject.put("status","success");
            jsonObject.put("msg", "修改成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "修改失败，请重新修改！");
        }
        return jsonObject;
    }

    public boolean updateStudent(String sid, String name, String password, Date date) {
        return studentDb.updateStudent(Integer.valueOf(sid), name, password, new Timestamp(date.getTime())) > 0;
    }

    public Teacher selectTeacherById(String tid) {
        return teacherDb.findById(Integer.valueOf(tid)).get();
    }

    public List<Homework> getHomeworkOfTeacher(String tid) {
        return homeworkDb.getHomeworkOfTeacher(Integer.valueOf(tid));
    }

    public List<Map<String, String>> getStudentHomeworkByHid(String hid) {
        List<Object[]> lists = studentHomeworkDb.getStudentHomeworkByHid(Integer.valueOf(hid));
        List<Map<String, String>> mapList = new ArrayList<>();
        for (Object[] objects : lists){
            Map<String, String> map = new HashMap<String, String>();
            map.put("sid", objects[0].toString());
            map.put("title", objects[1].toString());
            map.put("content", objects[2].toString());
            map.put("create_time", objects[3].toString());
            map.put("update_time", objects[4].toString());
            map.put("submit_content", objects[5] == null ? "未提交" : objects[5].toString());
            map.put("submit_time", objects[6] == null ? "未提交" : objects[6].toString());
            mapList.add(map);
        }
        return mapList;
    }

}
