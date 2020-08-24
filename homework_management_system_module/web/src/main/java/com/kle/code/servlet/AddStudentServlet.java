package com.kle.code.servlet;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;
import com.kle.code.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/teacher/addStudent")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        req.setAttribute("tid", tid);
//        List<Student> studentList = StudentHomeworkDb.getOtherStudent(tid);
//        req.setAttribute("list", studentList);
        req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/addStudent.jsp").forward(req, resp);
    }

}
