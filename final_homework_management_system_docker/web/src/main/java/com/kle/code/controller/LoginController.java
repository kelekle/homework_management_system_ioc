package com.kle.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.service.impl.StudentServiceImpl;
import com.kle.code.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 登陆、登出、注册路由
 * @author ypb
 */
@Controller
public class LoginController {

    private final StudentServiceImpl studentService;

    private final TeacherServiceImpl teacherService;

    @Autowired
    public LoginController(StudentServiceImpl studentService, TeacherServiceImpl teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(req.getContextPath() + "login.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(req.getContextPath() + "login.jsp").forward(req, resp);
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

    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String studentLogin(@RequestParam("username") String sid, @RequestParam("password") String password,
                             HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        JSONObject jsonObject = studentService.login(sid, password);
        if("success".equals(jsonObject.get("status"))) {
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "student_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid, "utf-8"));
            Cookie passwordCookie = new Cookie("password", URLEncoder.encode(password, "utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/teacherLogin", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String teacherLogin(@RequestParam("username") String sid, @RequestParam("password") String password,
                             HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        JSONObject jsonObject = teacherService.login(sid, password);
        if("success".equals(jsonObject.get("status"))){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "teacher_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/studentRegister", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String studentRegister(@RequestParam("s_name") String name, @RequestParam("s_password") String password) throws ServletException, IOException {
        Date date = new Date();
        JSONObject jsonObject = studentService.addStudent(name, password, date, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/studentRegister", method = RequestMethod.GET)
    public void studentRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "studentRegister.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/teacherRegister", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String teacherRegister(@RequestParam("t_name") String name, @RequestParam("t_password") String password,
                                  HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        JSONObject jsonObject = teacherService.addTeacher(name, password, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/teacherRegister", method = RequestMethod.GET)
    public void teacherRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "teacherRegister.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/register-success", method = RequestMethod.GET)
    public void registerSuccess(@RequestParam("id") String id,
                                HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", id);
        req.getRequestDispatcher(req.getContextPath() + "registerSuccess.jsp").forward(req, resp);
    }

}
