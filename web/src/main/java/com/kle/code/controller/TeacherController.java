package com.kle.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @RequestMapping(path = "/addHomework", method = RequestMethod.POST)
    public void addHomework(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = null;
        out = resp.getWriter();
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

    @RequestMapping(value = "/addHomework", method = RequestMethod.GET)
    public void addHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        List<Student> studentList = new StudentDb().getStudentOfTeacher(tid);
        req.setAttribute("tid", tid);
        req.setAttribute("student_list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addHomework.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Date date = new Date();
        if(new StudentDb().addStudent(tid, name, password, date, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.GET)
    public void addStudentView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        req.setAttribute("tid", tid);
//        List<Student> studentList = StudentHomeworkDb.getOtherStudent(tid);
//        req.setAttribute("list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/deleteHomework", method = RequestMethod.GET)
    public void deleteHomework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        String hid = req.getParameter("hid");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(new HomeworkDb().deleteHomework(hid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    public void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        String sid = req.getParameter("sid");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(new StudentDb().deleteStudent(sid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/editHomework", method = RequestMethod.GET)
    public void editHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hid = req.getParameter("hid");
        String tid = req.getParameter("tid");
        Homework studentHomework = new HomeworkDb().selectHomeworkById(hid);
        if(studentHomework != null){
            req.setAttribute("tid", tid);
            req.setAttribute("homework", studentHomework);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/teacher/teacherHome");
        }
    }

    @RequestMapping(value = "/editHomework", method = RequestMethod.POST)
    public void editHomework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        String hid = req.getParameter("hid");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Date date = new Date();
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(new HomeworkDb().updateHomework(hid, title, content, date)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.GET)
    public void editStudentView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        Student student = new StudentDb().selectStudentById(sid);
        req.setAttribute("student", student);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.POST)
    public void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Date date = new Date();
        if(new StudentDb().updateStudent(sid, name, password, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/teacherHome", method = RequestMethod.GET)
    public void teacherHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String tid = null;
        for(Cookie c : cookies){
            if("username".equals(c.getName())){
                tid = URLDecoder.decode(c.getValue(), "utf-8");
            }
        }
        if(tid != null){
            Teacher s = new TeacherDb().selectTeacherById(tid);
            if(s == null){
                resp.sendRedirect("index.jsp");
            }else {
                List<Student> list = new StudentDb().getStudentOfTeacher(tid);
                req.setAttribute("teacher", s);
                req.setAttribute("student_list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHome.jsp").forward(req, resp);
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @RequestMapping(value = "/teacherHomework", method = RequestMethod.GET)
    public void teacherHomework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        if(tid != null) {
            List<Homework> homeworkList = new HomeworkDb().getHomeworkOfTeacher(tid);
            Teacher teacher = new TeacherDb().selectTeacherById(tid);
            req.setAttribute("teacher", teacher);
            req.setAttribute("list", homeworkList);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/teacherHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @RequestMapping(value = "/studentHomework", method = RequestMethod.GET)
    public void studentHomework(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hid = req.getParameter("hid");
        String tid = req.getParameter("tid");
        Teacher teacher = new TeacherDb().selectTeacherById(tid);
        List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkByHid(hid);
        req.setAttribute("teacher", teacher);
        req.setAttribute("list", list);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/studentHomework.jsp").forward(req, resp);
    }

}
