package com.kle.code.controller;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                if("student_true".equals(c.getValue())){
                    c.setValue("student_false");
                }else {
                    c.setValue("teacher_false");
                }
                cookie = c;
                break;
            }
        }
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
    public void studentLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("username");
        String password = req.getParameter("password");
        Student s = new StudentDb().studentLogin(sid, password);
        if(s != null){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "student_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
            List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkOfStudent(sid);
            req.setAttribute("student", s);
            req.setAttribute("list", list);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
        }else {
            System.out.println("fail");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @RequestMapping(value = "/teachertLogin", method = RequestMethod.POST)
    public void teacherLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("username");
        String password = req.getParameter("password");
        Teacher s = new TeacherDb().teacherLogin(sid, password);
        if(s != null){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "teacher_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
            List<Student> studentList = new StudentDb().getStudentOfTeacher(sid);
            req.setAttribute("teacher", s);
            req.setAttribute("student_list", studentList);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHome.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @RequestMapping(value = "/teachertRegister", method = RequestMethod.POST)
    public void teacherRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("t_name");
        String password = req.getParameter("t_password");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Date date = new Date();
        int tid = new TeacherDb().addTeacher(name, password, date);
        if(tid > 0){
            req.setAttribute("tid", tid);
            req.getRequestDispatcher(req.getContextPath() + "/registerSuccess.jsp").forward(req, resp);
        }else {
            req.setAttribute("status","fail");
            req.getRequestDispatcher(req.getContextPath() + "/register.jsp").forward(req, resp);
        }
    }

}
