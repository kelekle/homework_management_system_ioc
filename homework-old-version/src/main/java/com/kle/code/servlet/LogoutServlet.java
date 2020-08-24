package com.kle.code.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                if("student_true".equals(c.getValue())){
                    c.setValue("student_false");
                }else {
                    c.setValue("teacher_false");
                }
                cookie = c;
                break;
            }
        }
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

}
