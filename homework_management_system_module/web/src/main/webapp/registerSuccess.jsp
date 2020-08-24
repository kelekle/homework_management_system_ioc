<%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/13
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册成功</title>
    <style>
        .success{
            height: 280px;
            width: 500px;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-top: -140px;
            margin-left: -250px;
        }
    </style>
</head>
<body>

<div class="success">
    <h1>恭喜您注册成功，您的账号ID为<%=request.getAttribute("tid")%></h1>
    <a href="login.jsp">返回登录</a>
</div>

</body>
</html>
