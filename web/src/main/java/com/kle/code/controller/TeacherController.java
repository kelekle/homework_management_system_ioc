package com.kle.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;
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
import java.util.ArrayList;
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

    private final HomeworkDb homeworkDb;

    private final StudentDb studentDb;

    private final StudentHomeworkDb studentHomeworkDb;

    private final TeacherDb teacherDb;

    @Autowired
    public TeacherController(HomeworkDb homeworkDb, StudentDb studentDb, StudentHomeworkDb studentHomeworkDb, TeacherDb teacherDb) {
        this.homeworkDb = homeworkDb;
        this.studentDb = studentDb;
        this.studentHomeworkDb = studentHomeworkDb;
        this.teacherDb = teacherDb;
    }

    @RequestMapping(path = "/addHomework", method = RequestMethod.POST, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String addHomework(@RequestParam("title") String title, @RequestParam("content") String content,
                              @RequestParam("tid") String tid, HttpServletRequest req){
        String[] idList = req.getParameterValues("idList");
        Date date = new Date();
        JSONObject jsonObject = new JSONObject();
        Homework homework = homeworkDb.addHomework(tid, title, content, date, date);
        List<String> exceptionList = new ArrayList<>();
        boolean correct = true;
        if(homework != null){
            for(String id : idList){
                if(!studentHomeworkDb.addStudentHomework(id, String.valueOf(homework.getHid()))){
                    correct = false;
                    exceptionList.add(id);
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
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/addHomework", method = RequestMethod.GET)
    public void addHomeworkView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        List<Student> studentList = studentDb.getStudentOfTeacher(tid);
        req.setAttribute("tid", tid);
        req.setAttribute("student_list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addHomework.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public void addStudent(@RequestParam("tid") String tid, @RequestParam("name") String name,
                           @RequestParam("password") String password, HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        if(studentDb.addStudent(tid, name, password, date, date)){
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
//        List<Student> studentList = StudentHomeworkDb.getOtherStudent(tid);
//        req.setAttribute("list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/deleteHomework", method = RequestMethod.GET, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteHomework(HttpServletRequest req) {
        String hid = req.getParameter("hid");
        JSONObject jsonObject = new JSONObject();
        if(homeworkDb.deleteHomework(hid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET, produces = "text/json; charset=utf-8")
    @ResponseBody
    public String deleteStudent(HttpServletRequest req) {
        String sid = req.getParameter("sid");
        JSONObject jsonObject = new JSONObject();
        if(studentDb.deleteStudent(sid)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/editHomework", method = RequestMethod.GET)
    public void editHomeworkView(@RequestParam("hid") String hid, @RequestParam("tid") String tid,
                                   HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Homework studentHomework = homeworkDb.selectHomeworkById(hid);
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
                             HttpServletRequest req) {
        String content = req.getParameter("content");
        Date date = new Date();
        JSONObject jsonObject = new JSONObject();
        if(homeworkDb.updateHomework(hid, title, content, date)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "修改成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "修改失败，请重新修改！");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.GET)
    public void editStudentView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        Student student = studentDb.selectStudentById(sid);
        req.setAttribute("student", student);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editStudent.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/editStudent", method = RequestMethod.POST)
    public void editStudent(@RequestParam("sid") String sid, @RequestParam("name") String name,
                              @RequestParam("password") String password, HttpServletRequest req,
                              HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        if(studentDb.updateStudent(sid, name, password, date)){
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
            Teacher s = teacherDb.selectTeacherById(tid);
            if(s == null){
                resp.sendRedirect("index.jsp");
            }else {
                List<Student> list = studentDb.getStudentOfTeacher(tid);
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
            List<Homework> homeworkList = homeworkDb.getHomeworkOfTeacher(tid);
            Teacher teacher = teacherDb.selectTeacherById(tid);
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
        Teacher teacher = teacherDb.selectTeacherById(tid);
        List<Map<String, String>> list = studentHomeworkDb.getStudentHomeworkByHid(hid);
        req.setAttribute("teacher", teacher);
        req.setAttribute("list", list);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/studentHomework.jsp").forward(req, resp);
    }

}
