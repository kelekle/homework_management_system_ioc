<%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/13
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作业管理系统</title>
    <link rel="stylesheet" href="static/layui/css/layui.css">
    <style>
        body{margin: 10px;}
        .layui-input{
            border-radius: 5px;
            width: 380px;
            height: 40px;
            font-size: 15px;
        }
        .layui-form-item{
            margin-left: 20px;
        }
        .layui-btn{
            border-radius: 5px;
            width: 380px;
            height: 40px;
            font-size: 15px;
        }
        a:hover{
            text-decoration: underline;
        }
        .register{
            border-radius: 4px;
            border-style: solid;
            border-color: #0C0C0C;
            border-width: 1px;
            position: relative;
            margin-top: 80px;
            padding: 20px;
            left: 65%;
            height: 300px;
            width: 420px;
        }
        /*.layui-form-label{*/
        /*  !*margin-left: -40px;*!*/
        /*}*/
    </style>
</head>
<body>

<div class="register" lay-filter="demo">
            <form class="layui-form" action="${pageContext.request.contextPath}/teacherRegister" method="post">
                <div class="container">
                    <div class="layui-form-label" style="margin-left: -40px;">名称</div>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="t_name" type="text" name="t_name" required  lay-verify="required" placeholder="请输入您的名称" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-label" style="margin-left: -40px;">密码</div>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="t_password" type="password" name="t_password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <button class="layui-btn" lay-submit lay-filter="formDemo">注册</button>
                    </div>
                </div>
            </form>
</div>
<script src="static/layui/layui.all.js"></script>
<script>
    <%if("fail".equals(request.getAttribute("status"))){%>
        layer.msg("注册失败，请重新注册！");
    <%}%>
</script>
</body>
</html>