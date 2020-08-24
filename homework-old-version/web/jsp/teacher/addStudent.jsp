<%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/10
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加学生</title>
    <link rel="stylesheet" href="../../static/layui/css/layui.css">
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
        <a href="${pageContext.request.contextPath}/teacher/teacherHome"><img src="../../static/images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../../static/images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<div class="homework">
    <h2 style="font-weight: bold; text-align: center">添加学生</h2>
    <form class="layui-form" style="height: 230px; margin-top: 10px;" action="${pageContext.request.contextPath}/teacher/addStudent" method="post">
        <div class="container">
        <div class="layui-form-label" style="margin-left: -60px">名称</div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="name" required lay-verify="required" placeholder="请填写名字" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-label" style="margin-left: -60px">密码</div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="password" required lay-verify="required" placeholder="请设置密码" autocomplete="off" class="layui-input">
            </div>
        </div>
            <div class="layui-form-item" style="height: 0; visibility: hidden">
                <div class="layui-input-inline">
                    <input type="text" name="tid" required value=<%=request.getAttribute("tid")%>>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="demo2">添加</button>
        </div>
    </form>
</div>

<script src="../../static/layui/layui.all.js"></script>
<script>
    <% String msg = (String) request.getAttribute("msg");
       if("success".equals(msg)){%>
    layer.msg("添加成功,1s后自动跳转至首页！");
    setTimeout("location.href='/teacher/teacherHome'", 1200);
    <%
        } else if("fail".equals(msg)){%>
    layer.alert("添加失败，请重新添加！！");
    <%  }
    %>
</script>

</body>
</html>
