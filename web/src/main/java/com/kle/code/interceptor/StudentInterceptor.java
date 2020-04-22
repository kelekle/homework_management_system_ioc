package com.kle.code.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学生身份拦截器
 * @author ypb
 */
public class StudentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String loginStatus = "";
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                loginStatus = c.getValue();
                if ("student_true".equals(loginStatus)) {
                    return true;
                }
                break;
            }
        }
        response.sendRedirect("login.jsp");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
