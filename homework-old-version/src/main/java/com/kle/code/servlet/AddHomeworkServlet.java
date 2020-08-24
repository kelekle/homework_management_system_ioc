package com.kle.code.servlet;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/teacher/addHomework")
public class AddHomeworkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        String[] idList = req.getParameterValues("idList");
        String title = req.getParameter("title");
        String content= req.getParameter("content");
        String tid= req.getParameter("tid");
        System.out.println("666666666666666666666");
        Date date = new Date();
        JSONObject jsonObject = new JSONObject();
        Homework homework = new HomeworkDb().addHomework(tid, title, content, date, date);
        List<String> exceptionList = new ArrayList<>();
        boolean correct = true;
        if(homework != null){
            for(String id : idList){
                if(!new StudentHomeworkDb().addStudentHomework(id, String.valueOf(homework.getHid()))){
                    correct = false;
                    exceptionList.add(id);
                    System.out.println("sssid: " + id);
                }
            }
            if(!correct){
                jsonObject.put("status", "fail");
                StringBuilder msg = new StringBuilder("[");
                for(String exception : exceptionList){
                    msg.append(exception);
                    msg.append(",");
                }
                msg.append("]");
                jsonObject.put("msg", "向ID为" + msg.toString() + "的同学布置作业失败！");
            }else {
                jsonObject.put("status", "success");
                jsonObject.put("msg","作业布置成功！");
            }
        }else {
            jsonObject.put("status", "fail");
            jsonObject.put("msg","插入异常，请重新尝试！");
        }
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        List<Student> studentList = new StudentDb().getStudentOfTeacher(tid);
        req.setAttribute("tid", tid);
        req.setAttribute("student_list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addHomework.jsp").forward(req, resp);
    }

}
