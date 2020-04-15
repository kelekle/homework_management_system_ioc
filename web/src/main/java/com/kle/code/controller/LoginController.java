package com.kle.code.controller;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登陆登出路由
 * @author ypb
 */
@Controller
public class LoginController {

    private final StudentDb studentDb;

    private final StudentHomeworkDb studentHomeworkDb;

    private final TeacherDb teacherDb;

    @Autowired
    public LoginController(StudentDb studentDb, StudentHomeworkDb studentHomeworkDb, TeacherDb teacherDb) {
        this.studentDb = studentDb;
        this.studentHomeworkDb = studentHomeworkDb;
        this.teacherDb = teacherDb;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void editStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        resp.sendRedirect("login.jsp");
    }

    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
    public void studentLogin(@RequestParam("username") String sid, @RequestParam("password") String password,
                             HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Student s = studentDb.studentLogin(sid, password);
        if(s != null){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "student_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
            List<Map<String, String>> list = studentHomeworkDb.getStudentHomeworkOfStudent(sid);
            req.setAttribute("student", s);
            req.setAttribute("list", list);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
        }else {
            resp.sendRedirect("index.jsp");
        }
    }

    @RequestMapping(value = "/teacherLogin", method = RequestMethod.POST)
    public void teacherLogin(@RequestParam("username") String sid, @RequestParam("password") String password,
                             HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Teacher s = teacherDb.teacherLogin(sid, password);
        if(s != null){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "teacher_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
            List<Student> studentList = studentDb.getStudentOfTeacher(sid);
            req.setAttribute("teacher", s);
            req.setAttribute("student_list", studentList);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHome.jsp").forward(req, resp);
        }else {
            resp.sendRedirect("index.jsp");
        }
    }

    @RequestMapping(value = "/teacherRegister", method = RequestMethod.POST)
    public void teacherRegister(@RequestParam("t_name") String name, @RequestParam("t_password") String password,
                                HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        int tid = teacherDb.addTeacher(name, password, date);
        if(tid > 0){
            req.setAttribute("tid", tid);
            req.getRequestDispatcher(req.getContextPath() + "registerSuccess.jsp").forward(req, resp);
        }else {
            req.setAttribute("status","fail");
            req.getRequestDispatcher(req.getContextPath() + "register.jsp").forward(req, resp);
        }
    }

}
