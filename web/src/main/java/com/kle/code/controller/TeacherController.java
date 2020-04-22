package com.kle.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import com.kle.code.service.impl.StudentServiceImpl;
import com.kle.code.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 老师操作路由
 * @author ypb
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final StudentServiceImpl studentService;

    private final TeacherServiceImpl teacherService;

    @Autowired
    public TeacherController(StudentServiceImpl studentService, TeacherServiceImpl teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @RequestMapping(path = "/addHomework", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String addHomework(@RequestParam("title") String title, @RequestParam("content") String content,
                              @RequestParam("tid") String tid, HttpServletRequest req) throws SQLException {
        String[] idList = req.getParameterValues("idList");
        Date date = new Date();
        JSONObject jsonObject = teacherService.addHomework(idList, tid, title, content, date, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/addHomework", method = RequestMethod.GET)
    public void addHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        List<Student> studentList = teacherService.getStudentOfTeacher(tid);
        req.setAttribute("tid", tid);
        req.setAttribute("student_list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addHomework.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public void addStudent(@RequestParam("tid") String tid, @RequestParam("name") String name,
                           @RequestParam("password") String password, HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        if(teacherService.addStudent(tid, name, password, date, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.GET)
    public void addStudentView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        req.setAttribute("tid", tid);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/deleteHomework", method = RequestMethod.GET, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteHomework(HttpServletRequest req) throws SQLException {
        String hid = req.getParameter("hid");
        JSONObject jsonObject = teacherService.deleteHomework(hid);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteStudent(HttpServletRequest req) throws SQLException {
        String sid = req.getParameter("sid");
        JSONObject jsonObject = teacherService.deleteStudent(sid);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/editHomework", method = RequestMethod.GET)
    public void editHomeworkView(@RequestParam("hid") String hid, @RequestParam("tid") String tid,
                                   HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Homework studentHomework = teacherService.selectHomeworkById(hid);
        if(studentHomework != null){
            req.setAttribute("tid", tid);
            req.setAttribute("homework", studentHomework);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/teacher/teacherHome");
        }
    }

    @RequestMapping(value = "/editHomework", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String editHomework(@RequestParam("hid") String hid, @RequestParam("title") String title,
                             HttpServletRequest req) throws SQLException {
        String content = req.getParameter("content");
        Date date = new Date();
        JSONObject jsonObject = teacherService.updateHomework(hid, title, content, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.GET)
    public void editStudentView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        Student student = studentService.selectStudentById(sid);
        req.setAttribute("student", student);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.POST)
    public void editStudent(@RequestParam("sid") String sid, @RequestParam("name") String name,
                              @RequestParam("password") String password, HttpServletRequest req,
                              HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Date date = new Date();
        if(teacherService.updateStudent(sid, name, password, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/teacherHome", method = RequestMethod.GET)
    public void teacherHome(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String tid = null;
        for(Cookie c : cookies){
            if("username".equals(c.getName())){
                tid = URLDecoder.decode(c.getValue(), "utf-8");
            }
        }
        if(tid != null){
            Teacher s = teacherService.selectTeacherById(tid);
            if(s == null){
                resp.sendRedirect("index.jsp");
            }else {
                List<Student> list = teacherService.getStudentOfTeacher(tid);
                req.setAttribute("teacher", s);
                req.setAttribute("student_list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHome.jsp").forward(req, resp);
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @RequestMapping(value = "/teacherHomework", method = RequestMethod.GET)
    public void teacherHomework(@RequestParam("tid") String tid, HttpServletRequest req,
                                HttpServletResponse resp) throws ServletException, IOException {
        if(tid != null) {
            List<Homework> homeworkList = teacherService.getHomeworkOfTeacher(tid);
            Teacher teacher = teacherService.selectTeacherById(tid);
            req.setAttribute("teacher", teacher);
            req.setAttribute("list", homeworkList);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @RequestMapping(value = "/studentHomework", method = RequestMethod.GET)
    public void studentHomework(@RequestParam("hid") String hid, @RequestParam("tid") String tid,
                                  HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = teacherService.selectTeacherById(tid);
        List<Map<String, String>> list = teacherService.getStudentHomeworkByHid(hid);
        req.setAttribute("teacher", teacher);
        req.setAttribute("list", list);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/studentHomework.jsp").forward(req, resp);
    }

}
