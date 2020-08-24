package com.kle.code.servlet;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/teacherRegister")
public class TeacherRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("t_name");
        String password = req.getParameter("t_password");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Date date = new Date();
        int tid = new TeacherDb().addTeacher(name, password, date);
        if(tid > 0){
            req.setAttribute("tid", tid);
            req.getRequestDispatcher(req.getContextPath() + "/registerSuccess.jsp").forward(req, resp);
        }else {
            req.setAttribute("status","fail");
            req.getRequestDispatcher(req.getContextPath() + "/register.jsp").forward(req, resp);
        }

    }

}
