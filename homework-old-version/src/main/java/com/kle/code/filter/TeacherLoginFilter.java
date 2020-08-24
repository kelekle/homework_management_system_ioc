package com.kle.code.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

public class TeacherLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8"); //防止中文乱码
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        String loginStatus = "";
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                loginStatus = c.getValue();
                if ("teacher_true".equals(loginStatus)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
                break;
            }
        }
        response.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {

    }

}
