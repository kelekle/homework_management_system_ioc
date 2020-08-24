package com.kle.code.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.dao.*;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import com.kle.code.service.TeacherService;
import com.kle.code.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ypb
 * 教师服务类
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final StudentMapper studentMapper;

    private final HomeworkMapper homeworkMapper;

    private final StudentHomeworkMapper studentHomeworkMapper;

    private final TeacherMapper teacherMapper;

    private final TeachMapper teachMapper;

    @Autowired
    public TeacherServiceImpl(StudentMapper studentMapper, HomeworkMapper homeworkMapper, StudentHomeworkMapper studentHomeworkMapper, TeacherMapper teacherMapper, TeachMapper teachMapper) {
        this.studentMapper = studentMapper;
        this.homeworkMapper = homeworkMapper;
        this.studentHomeworkMapper = studentHomeworkMapper;
        this.teacherMapper = teacherMapper;
        this.teachMapper = teachMapper;
    }

    @Override
    public JSONObject login(String tid, String password){
        JSONObject jsonObject = new JSONObject();
        Teacher teacher = teacherMapper.selectByPrimaryKey(Integer.parseInt(tid));
        if(teacher == null){
            jsonObject.put("status", "fail");
            jsonObject.put("msg", "用户不存在！");
            return jsonObject;
        }
        if(teacher.getPassword().equals(password)){
            jsonObject.put("status", "success");
            return jsonObject;
        }else {
            jsonObject.put("status", "fail");
            jsonObject.put("msg", "密码错误");
        }
        return jsonObject;
    }

    @Override
    public List<Student> getStudentOfTeacher(String tid){
        return studentMapper.selectStudentOfTeacher(Integer.valueOf(tid));
    }

    @Override
    public List<Student> getNotMyStudent(String tid){
        return studentMapper.selectNotMyStudent(Integer.valueOf(tid));
    }

    @Override
    public JSONObject addTeacher(String name, String password, Date date){
        JSONObject jsonObject = new JSONObject();
        Teacher teacher = SpringContextUtil.getBean("teacher");
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        if(teacherMapper.insert(teacher) > 0){
            jsonObject.put("status", "success");
            jsonObject.put("id", teacher.getTid());
        }else {
            jsonObject.put("status", "fail");
            jsonObject.put("msg", "注册失败！");
        }
        return jsonObject;
    }

    @Override
    @Transactional
    public JSONObject addHomework(String tid, String title, String content, Date createTime, Date updateTime) {
        JSONObject jsonObject = new JSONObject();
        Homework homework = SpringContextUtil.getBean("homework");
        homework.setTitle(title);
        homework.setContent(content);
        homework.setTid(Integer.parseInt(tid));
        homework.setUpdateTime(updateTime);
        homework.setCreateTime(createTime);
        boolean correct = true;
        List<Student> students = getStudentOfTeacher(tid);
        if(homeworkMapper.insert(homework) > 0){
            for(Student student : students){
                if(studentHomeworkMapper.insert(student.getSid(), homework.getHid()) <= 0){
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

    @Override
    public boolean teachStudent(String tid, String[] sidList, Date date) {
        for (String id : sidList){
            teachMapper.insert(Integer.valueOf(id), Integer.parseInt(tid), date);
        }
        return true;
    }

    @Override
    @Transactional
    public JSONObject deleteHomework(String hid) {
        JSONObject jsonObject = new JSONObject();
        int id1 = homeworkMapper.deleteByPrimaryKey(Integer.valueOf(hid));
        int id2 = studentHomeworkMapper.deleteByHid(Integer.valueOf(hid));
        if(id1 >= 0 && id2 >= 0){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject;
    }

    @Override
    @Transactional
    public JSONObject removeStudent(String tid, String sid) {
        JSONObject jsonObject = new JSONObject();
        if(teachMapper.deleteByPrimaryKey(Integer.valueOf(sid), Integer.valueOf(tid)) >= 0
                &&studentHomeworkMapper.deleteBySid(Integer.valueOf(sid)) >= 0){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject;
    }

    @Override
    public Homework selectHomeworkById(String hid) {
        return homeworkMapper.selectByPrimaryKey(Integer.valueOf(hid));
    }

    @Override
    public JSONObject updateHomework(String hid, String title, String content, Date date) {
        JSONObject jsonObject = new JSONObject();
        if(homeworkMapper.updateByPrimaryKey(Integer.valueOf(hid), title, content, new Timestamp(date.getTime())) > 0){
            jsonObject.put("status","success");
            jsonObject.put("msg", "修改成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "修改失败，请重新修改！");
        }
        return jsonObject;
    }

    @Override
    public Teacher selectTeacherById(String tid) {
        return teacherMapper.selectByPrimaryKey(Integer.parseInt(tid));
    }

    @Override
    public List<Homework> getHomeworkOfTeacher(String tid) {
        return homeworkMapper.selectHomeworksOfTeacher(Integer.valueOf(tid));
    }

    @Override
    public List<Map<String, String>> getStudentHomeworkByHid(String hid) {
        List<Map<String, String>> res = new ArrayList<>();
        List<Map<String, Object>> mapList = studentHomeworkMapper.selectStudentHomeworkByHid(Integer.valueOf(hid));
        for (Map<String, Object> map : mapList){
            Map<String, String> map1 = new HashMap<>();
            map1.put("hid", hid);
            map1.put("sid", String.valueOf(map.get("sid")));
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

}
