package com.kle.code.servlet;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.model.Homework;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/teacher/editHomework")
public class EditHomeworkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hid = req.getParameter("hid");
        String tid = req.getParameter("tid");
        Homework studentHomework = new HomeworkDb().selectHomeworkById(hid);
        if(studentHomework != null){
            req.setAttribute("tid", tid);
            req.setAttribute("homework", studentHomework);
            req.getRequestDispatcher(req.getContextPath() + "/jsp/teacher/editHomework.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath() + "/teacher/teacherHome");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        String hid = req.getParameter("hid");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Date date = new Date();
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(new HomeworkDb().updateHomework(hid, title, content, date)){
            jsonObject.put("status","success");
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("status","fail");
            jsonObject.put("msg", "删除失败，请重新删除！");
        }
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }

}
