package com.kle.code.service;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教师服务接口
 * @author ypb
 */
public interface TeacherService {

    /**
     * 登录
     * @param tid
     * @param password
     * @return
     */
    JSONObject login(String tid, String password);

    /**
     * 获得学生列表
     * @param tid
     * @return
     */
    List<Student> getStudentOfTeacher(String tid);

    List<Student> getNotMyStudent(String tid);

    /**
     * 添加教师
     * @param name
     * @param password
     * @param date
     * @return
     */
    JSONObject addTeacher(String name, String password, Date date);

    /**
     * 布置作业
     * @param tid
     * @param title
     * @param content
     * @param createTime
     * @param updateTime
     * @return
     */
    JSONObject addHomework(String tid, String title, String content, Date createTime, Date updateTime);

    /**
     * 某一教师给自己班级添加学生，形成教学关系
     * @param tid
     * @param sid
     * @param date
     * @return
     */
    boolean teachStudent(String tid, String[] sid, Date date);

    /**
     * 删除作业
     * @param hid
     * @return
     */
    JSONObject deleteHomework(String hid);

    /**
     * 删除学生
     * @param tid
     * @param sid
     * @return
     */
    JSONObject removeStudent(String tid, String sid);

    /**
     * 选择作业
     * @param hid
     * @return
     */
    Homework selectHomeworkById(String hid);

    /**
     * 更新作业内容
     * @param hid
     * @param title
     * @param content
     * @param date
     * @return
     */
    JSONObject updateHomework(String hid, String title, String content, Date date);

    /**
     * 选择教师
     * @param tid
     * @return
     */
    Teacher selectTeacherById(String tid);

    /**
     * 教师的作业列表
     * @param tid
     * @return
     */
    List<Homework> getHomeworkOfTeacher(String tid);

    /**
     * 学生完成情况
     * @param hid
     * @return
     */
    List<Map<String, String>> getStudentHomeworkByHid(String hid);

}
