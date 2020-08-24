package com.kle.code.servlet;

import com.kle.code.db.StudentDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@WebServlet("/studentLogin")
public class StudentLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("username");
        String password = req.getParameter("password");
        Student s = new StudentDb().studentLogin(sid, password);
        if(s != null){
            //使用Cookies对象保存字符串
            Cookie loginStatusCookie = new Cookie("loginStatus", "student_true");
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(sid,"utf-8"));
            Cookie passwordCookie = new Cookie("password",URLEncoder.encode(password,"utf-8"));
            resp.addCookie(loginStatusCookie);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
            List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkOfStudent(sid);
            req.setAttribute("student", s);
            req.setAttribute("list", list);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
        }else {
            System.out.println("fail");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }


}
