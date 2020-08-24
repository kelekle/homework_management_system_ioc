package com.kle.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import com.kle.code.service.StudentService;
import com.kle.code.service.TeacherService;
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

    private final TeacherService teacherService;

    public TeacherController(TeacherServiceImpl teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(path = "/new-homework", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String addHomework(@RequestParam("title") String title, @RequestParam("content") String content,
                              @RequestParam("tid") String tid, HttpServletRequest req) throws SQLException {
        Date date = new Date();
        JSONObject jsonObject = teacherService.addHomework(tid, title, content, date, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/new-homework", method = RequestMethod.GET)
    public void homework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        req.setAttribute("tid", tid);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addHomework.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/homework", method = RequestMethod.DELETE, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteHomework(HttpServletRequest req) throws SQLException {
        String hid = req.getParameter("hid");
        JSONObject jsonObject = teacherService.deleteHomework(hid);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/homework", method = RequestMethod.GET)
    public void updateHomework(@RequestParam("hid") String hid, @RequestParam("tid") String tid,
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

    @RequestMapping(value = "/homework", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String updateHomework(@RequestParam("hid") String hid, @RequestParam("title") String title,
                                 @RequestParam("content") String content, HttpServletRequest req) throws SQLException {
        Date date = new Date();
        JSONObject jsonObject = teacherService.updateHomework(hid, title, content, date);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/new-student", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String addStudent(@RequestParam("tid") String tid, HttpServletRequest req,
                             HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        String[] idList = req.getParameterValues("idList");
        Date date = new Date();
        if(teacherService.teachStudent(tid, idList, date)){
            jsonObject.put("status", "success");
        }else {
            jsonObject.put("status","fail");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/new-student", method = RequestMethod.GET)
    public void student(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        req.setAttribute("tid", tid);
        req.setAttribute("student_list", teacherService.getNotMyStudent(tid));
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addMyStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/student", method = RequestMethod.DELETE, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteStudent(HttpServletRequest req) throws SQLException {
        String tid = req.getParameter("tid");
        String sid = req.getParameter("sid");
        JSONObject jsonObject = teacherService.removeStudent(tid, sid);
        return jsonObject.toJSONString();
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
            if(s != null) {
                List<Student> list = teacherService.getStudentOfTeacher(tid);
                req.setAttribute("teacher", s);
                req.setAttribute("student_list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHome.jsp").forward(req, resp);
            }
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
