package com.kle.code.servlet;

import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Homework;
import com.kle.code.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/studentHomework")
public class TeacherStudentHomeworkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hid = req.getParameter("hid");
        String tid = req.getParameter("tid");
        Teacher teacher = new TeacherDb().selectTeacherById(tid);
        List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkByHid(hid);
        req.setAttribute("teacher", teacher);
        req.setAttribute("list", list);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/studentHomework.jsp").forward(req, resp);
    }
}
