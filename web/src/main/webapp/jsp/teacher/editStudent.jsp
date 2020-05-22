<%@ page import="com.kle.code.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/13
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Student student = (Student) request.getAttribute("student");%>
<html>
<head>
    <title>添加学生</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{overflow: hidden}
        .layui-input{
            border-radius: 5px;
            width: 380px;
            height: 40px;
            font-size: 15px;
        }
        .homework{ height:300px;width:550px; border-style: solid; border-color: #9F9F9F; border-width:1px; border-radius: 4px; padding: 20px; position:absolute;left: 50%; top: 15%; margin:0 0 0 -225px;}
    </style>
</head>
<body>

<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHome"><img src="../images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<div class="homework">
    <h2 style="font-weight: bold; text-align: center">编辑学生</h2>
    <form class="layui-form" style="height: 230px; margin-top: 10px;" action="${pageContext.request.contextPath}/teacher/editStudent" method="post">
        <div class="container">
            <div class="layui-form-label" style="margin-left: -60px">名称</div>
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <input type="text" name="name" required lay-verify="required" value="<%=student.getName()%>" placeholder="请填写名字" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-label" style="margin-left: -60px">密码</div>
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <input type="text" name="password" required lay-verify="required" value="<%=student.getPassword()%>" placeholder="请设置密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="height: 0; visibility: hidden">
                <div class="layui-input-inline">
                    <input type="text" name="sid" required value=<%=student.getSid()%>>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="demo2">完成</button>
        </div>

    </form>
</div>

<script src="../layui/layui.all.js"></script>
<script>
    <% String msg = (String) request.getAttribute("msg");
       if("success".equals(msg)){%>
    layer.msg("修改成功,1s后自动跳转至首页！");
    setTimeout("location.href='/teacher/teacherHome'", 1200);
    <%
        } else if("fail".equals(msg)){%>
    layer.alert("修改失败，请重新修改！！");
    <%  }
    %>
</script>

</body>
</html>
