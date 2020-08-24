package com.kle.code.servlet;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/student/submitHomework")
public class SubmitHomeworkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String submitContent = req.getParameter("submit_content");
        Date date = new Date();
        String sid = req.getParameter("sid");
        String hid = req.getParameter("hid");
        Map<String, String> map = new StudentHomeworkDb().selectStudentHomeworkById(sid, hid);
        req.setAttribute("studentHomework", map);
        if(new StudentHomeworkDb().submitHomework(sid, hid, submitContent, date)){
            req.setAttribute("msg","success");
        }else {
            req.setAttribute("msg","fail");
        }
        req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String hid = req.getParameter("hid");
        Map<String, String> map = new StudentHomeworkDb().selectStudentHomeworkById(sid, hid);
        if(map != null){
            req.setAttribute("studentHomework", map);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/submitHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/student/studentHome");
        }
    }

}
