package com.kle.code.servlet;

import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

@WebServlet("/teacher/teacherHomework")
public class TeacherHomeworkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

}
