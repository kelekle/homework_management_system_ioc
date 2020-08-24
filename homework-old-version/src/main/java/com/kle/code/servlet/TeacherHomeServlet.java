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
import java.net.URLDecoder;
import java.util.List;

@WebServlet("/teacher/teacherHome")
public class TeacherHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
