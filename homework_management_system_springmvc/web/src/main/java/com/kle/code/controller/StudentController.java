package com.kle.code.controller;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @RequestMapping(value = "/studentHome", method = RequestMethod.GET)
    public void studentLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String sid = null;
        for (Cookie c : cookies) {
            if ("username".equals(c.getName())) {
                sid = URLDecoder.decode(c.getValue(), "utf-8");
            }
        }
        if (sid != null) {
            Student s = new StudentDb().selectStudentById(sid);
            if (s == null) {
                resp.sendRedirect("index.jsp");
            } else {
                List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkOfStudent(sid);
                req.setAttribute("student", s);
                req.setAttribute("list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "login.jsp");
        }
    }

    @RequestMapping(value = "/submitHomework", method = RequestMethod.GET)
    public void submitHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String hid = req.getParameter("hid");
        Map<String, String> map = new StudentHomeworkDb().selectStudentHomeworkById(sid, hid);
        if(map != null){
            req.setAttribute("studentHomework", map);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/student/studentHome");
        }
    }

    @RequestMapping(value = "/submitHomework", method = RequestMethod.POST)
    public void submitHomework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String submitContent = req.getParameter("submit_content");
        System.out.println("con: " + submitContent);
        Date date = new Date();
        String sid = req.getParameter("sid");
        String hid = req.getParameter("hid");
        Map<String, String> map = new StudentHomeworkDb().selectStudentHomeworkById(sid, hid);
        req.setAttribute("studentHomework", map);
        if(new StudentHomeworkDb().submitHomework(sid, hid, submitContent, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
    }


}
