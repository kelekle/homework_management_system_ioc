package com.kle.code.servlet;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.HomeworkDb;
import com.kle.code.db.StudentHomeworkDb;
import com.kle.code.db.TeacherDb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/teacher/deleteHomework")
public class DeleteHomeworkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json; charset=utf-8");
        String hid = req.getParameter("hid");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(new HomeworkDb().deleteHomework(hid)){
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
