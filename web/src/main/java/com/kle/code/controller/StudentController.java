package com.kle.code.controller;

import com.kle.code.model.Student;
import com.kle.code.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学生操作路由
 * @author ypb
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/studentHome", method = RequestMethod.GET)
    public void studentLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String sid = null;
        for (Cookie c : cookies) {
            if ("username".equals(c.getName())) {
                sid = URLDecoder.decode(c.getValue(), "utf-8");
            }
        }
        if (sid != null) {
            Student s = studentService.selectStudentById(sid);
            if (s == null) {
                resp.sendRedirect("index.jsp");
            } else {
                List<Map<String, String>> list = studentService.getStudentHomeworkOfStudent(sid);
                req.setAttribute("student", s);
                req.setAttribute("list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @RequestMapping(value = "/submitHomework", method = RequestMethod.GET)
    public void submitHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String hid = req.getParameter("hid");
        Map<String, String> map = studentService.selectStudentHomeworkById(sid, hid);
        if(map != null){
            req.setAttribute("studentHomework", map);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/student/studentHome");
        }
    }

    @RequestMapping(value = "/submitHomework", method = RequestMethod.POST)
    public void submitHomework(@RequestParam("sid") String sid, @RequestParam("hid") String hid,
                                 HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String submitContent = req.getParameter("submit_content");
        Date date = new Date();
        Map<String, String> map = studentService.selectStudentHomeworkById(sid, hid);
        req.setAttribute("studentHomework", map);
        if(studentService.submitHomework(sid, hid, submitContent, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
    }

}
