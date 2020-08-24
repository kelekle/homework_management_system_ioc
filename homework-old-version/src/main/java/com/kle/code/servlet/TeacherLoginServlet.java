package com.kle.code.servlet;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Student;
import com.kle.code.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/teacherLogin")
public class TeacherLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

}
