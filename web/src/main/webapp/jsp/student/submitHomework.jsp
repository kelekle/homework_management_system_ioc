<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/9
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作业提交</title>
    <link rel="stylesheet" href="../../static/layui/css/layui.css">
    <style>
        body{overflow: hidden}
        .homework{ height:415px;width:800px;padding: 20px; border-style: solid; border-width: 1px; border-color:#9F9F9F; border-radius: 4px;position:absolute;left: 50%; top: 15%; margin:0 0 0 -400px;}
        .homework h1{ height:30px; word-wrap: break-word; overflow: hidden; text-align:left; color:#fff; font-size:20px; }
        .homework h4{ word-wrap: break-word; overflow: hidden; text-align:left; color:#fff; font-size:16px; margin-top: 10px;}
    </style>
</head>
<body>

<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/student/studentHome"><img src="../../static/images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../../static/images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<%Map<String, String> map = (Map<String, String>) request.getAttribute("studentHomework");%>

<div class="homework">
    <div style="height: 210px">
        <h1 style="font-weight: bold; text-align: center;color: #0C0C0C">提交作业</h1>
        <h3 style="margin-top: 10px">作业题目：&nbsp;&nbsp;<%=map.get("title")%></h3>
        <h4 style="max-height: 130px; height: 20px; color: #0C0C0C">作业内容：&nbsp;&nbsp;<%=map.get("content")%></h4>
        <h4 style="height: 20px; color: #0C0C0C">创建时间：&nbsp;&nbsp;<%=map.get("create_time")%></h4>
        <h4 style="height: 20px; color: #0C0C0C">更新时间：&nbsp;&nbsp;<%=map.get("update_time")%></h4>
    </div>
    <form class="layui-form layui-form-pane" style="height: 200px; margin-top: 10px;" action="${pageContext.request.contextPath}/student/submitHomework" method="post">
        <div class="layui-form-item layui-form-text" style="">
            <label class="layui-form-label">作答区</label>
            <div class="layui-input-block" >
                <% if(!"".equals(map.get("submit_content"))){%>
                <textarea placeholder="请输入内容" required name="submit_content" class="layui-textarea"><%=map.get("submit_content")%></textarea>
                <%}else{%>
                <textarea placeholder="请输入内容" required name="submit_content" class="layui-textarea"></textarea>
                <%}
                %>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="demo2">提交</button>
        </div>
        <div class="layui-form-item" style="height: 0; visibility: hidden">
            <input height="0" type="text" name="sid" required value=<%=map.get("sid")%> />
        </div>
        <div class="layui-form-item" style="height: 0; visibility: hidden">
            <input height="0" type="text" name="hid" required value=<%=map.get("hid")%> />
        </div>
    </form>
</div>

<script src="../../static/layui/layui.all.js"></script>
<script>
    <% String msg = (String) request.getAttribute("msg");
        if("success".equals(msg)){%>
            layer.msg("提交成功！");
            setTimeout("location.href='/student/studentHome'", 1200);
    <%
        } else if("fail".equals(msg)){%>
            layer.alert("提交失败，请重新提交！");
    <%  }
    %>
</script>

</body>
</html>
