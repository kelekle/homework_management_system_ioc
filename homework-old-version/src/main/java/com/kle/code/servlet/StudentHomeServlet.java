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
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@WebServlet("/student/studentHome")
public class StudentHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String sid = null;
        for(Cookie c : cookies){
            if("username".equals(c.getName())){
                sid = URLDecoder.decode(c.getValue(), "utf-8");
            }
        }
        if(sid != null){
            Student s = new StudentDb().selectStudentById(sid);
            if(s == null){
                resp.sendRedirect("index.jsp");
            }else {
                List<Map<String, String>> list = new StudentHomeworkDb().getStudentHomeworkOfStudent(sid);
                req.setAttribute("student", s);
                req.setAttribute("list", list);
                req.getRequestDispatcher(req.getContextPath() + "/jsp/student/studentHome.jsp").forward(req, resp);
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "login.jsp");
        }

    }
}
