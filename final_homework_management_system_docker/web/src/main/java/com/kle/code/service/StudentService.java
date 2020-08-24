package com.kle.code.service;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.model.Student;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学生服务接口
 * @author ypb
 */
public interface StudentService {

    /**
     * 登录
     * @param sid
     * @param password
     * @return
     */
    JSONObject login(String sid, String password);

    /**
     * 通过 id 选择学生
     * @param sid
     * @return
     */
    Student selectStudentById(String sid);

    JSONObject addStudent(String name, String password, Date date, Date date1);

    /**
     * 获得该学生的作业列表
     * @param sid
     * @return
     */
    List<Map<String, String>> getStudentHomeworkOfStudent(String sid);

    /**
     * 获得学生对某一作业的完成情况
     * @param sid
     * @param hid
     * @return
     */
    Map<String, String> selectStudentHomeworkById(String sid, String hid);

    /**
     * 学生提交作业
     * @param sid
     * @param hid
     * @param submitContent
     * @param date
     * @return
     */
    boolean submitHomework(String sid, String hid, String submitContent, Date date);

}
