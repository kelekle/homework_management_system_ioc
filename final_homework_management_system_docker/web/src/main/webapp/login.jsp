<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/11
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作业管理系统</title>
    <link rel="stylesheet" href="layui/css/layui.css">
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
        .font-set{
            font-size: 13px;
            text-decoration: none;
            margin-left: 100px;
        }
        a:hover{
            text-decoration: underline;
        }
        .layui-tab{
            position: relative;
            margin-top: 80px;
            left: 65%;
            height: 350px;
            width: 420px;
        }
        /*.layui-form-label{*/
        /*  !*margin-left: -40px;*!*/
        /*}*/
    </style>
</head>
<body>

<div class="layui-tab layui-tab-card" lay-filter="demo">
    <ul class="layui-tab-title">
        <li class="layui-this" style="width: 50%">学生</li>
        <li style="width: 50%">教师</li>
    </ul>

    <div class="layui-tab-content">

        <div class="layui-tab-item layui-show">
            <form class="layui-form" method="post">
                <div class="container">
                    <div class="layui-form-label" style="margin-left: -40px;">学号</div>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="s_username" type="text" name="username" lay-verify="required|number" placeholder="请输入学号" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <label class="layui-form-label" style="margin-left: -40px;">密码</label>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="s_password" type="password" name="password" lay-verify="required|pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <button class="layui-btn" lay-submit lay-filter="studentForm">登陆</button>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/studentRegister" class="font-set" style="text-decoration:underline;">还没有学生账号？赶快去注册一个！</a>
            <%--                <a href="" class="font-set">忘记密码?</a>  <a href="" class="font-set">立即注册</a>--%>
            </form>
        </div>

        <div class="layui-tab-item">
            <form class="layui-form" method="post">
                <div class="container">
                    <div class="layui-form-label" style="margin-left: -40px;">工号</div>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="t_username" type="text" name="username" lay-verify="required|number" placeholder="请输入教工号" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-label" style="margin-left: -40px;">密码</div>
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input id="t_password" type="password" name="password" lay-verify="required|pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
                        </div>
                        <!-- <div class="layui-form-mid layui-word-aux">辅助文字</div> -->
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <button class="layui-btn" lay-submit lay-filter="teacherForm">登陆</button>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/teacherRegister" class="font-set" style="text-decoration:underline;">还没有教工账号？赶快去注册一个！</a>
            </form>
        </div>

    </div>

</div>

<%
    request.setCharacterEncoding("utf-8"); //防止中文乱码
    Cookie[] cookies = request.getCookies();
    boolean studentLogged = false;
    boolean teacherLogged = false;
    String username = "";
    String password = "";
    if(cookies != null){
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                String loginStatus = c.getValue();
                if ("student_true".equals(loginStatus)) {
                    response.sendRedirect("/student/studentHome");
                } else if ("teacher_true".equals(loginStatus)) {
                    response.sendRedirect("/teacher/teacherHome");
                }
                break;
            }
        }
        for (Cookie c : cookies) {
            if ("loginStatus".equals(c.getName())) {
                String loginStatus = c.getValue();
                if ("student_false".equals(loginStatus)) {
                    studentLogged = true;
                } else if ("teacher_false".equals(loginStatus)) {
                    teacherLogged = true;
                }
                break;
            }
        }
        if(teacherLogged || studentLogged){
            //输入框显示账号密码
            for(Cookie c : cookies){
                if("username".equals(c.getName())){
                    username = URLDecoder.decode(c.getValue(), "utf-8");
                }
                if("password".equals(c.getName())){
                    password = URLDecoder.decode(c.getValue(), "utf-8");
                }
            }
        }
    }
%>
<% if(!username.equals("") && !password.equals("") && studentLogged){ %>
<script>
    document.getElementById("s_username").value = <%=username%>;
    document.getElementById("s_password").value = <%=password%>;
</script>
<%}%>
</body>
<script src="layui/layui.all.js"></script>
<script>
    layui.use('element', function(){
        var element = layui.element;
        //一些事件监听
        element.on('tab(demo)', function(data){
            if(data.index === 0){
                <% if(!username.equals("") && !password.equals("") && studentLogged){ %>
                document.getElementById("s_username").value = <%=username%>;
                document.getElementById("s_password").value = <%=password%>;
                <%}%>
            }else {
                <% if(!username.equals("") && !password.equals("") && teacherLogged){ %>
                document.getElementById("t_username").value = <%=username%>;
                document.getElementById("t_password").value = <%=password%>;
                <%}%>
            }
        });
    });
    layui.use(['form'], function () {
        var form = layui.form;
        //要放在form.on外面，千万不能放在提交步骤中，否则会不触发
        form.verify({
            pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
        });
        form.on("submit(teacherForm)", function (data) {
            var $ = layui.$;
            var username = $('#t_username').val();
            var password = $('#t_password').val();
            console.log(username);
            console.log(password);
            $.ajax({
                url: "/teacherLogin",
                type: "post",
                traditional: true,
                data: {
                    "username": username,
                    "password": password
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    //请求成功时处理
                    if(data.status === 'success') {
                        window.location.href="/teacher/teacherHome";
                    }else {
                        var msg = data.msg;
                        layer.alert(msg);
                    }
                }, error:function(){
                    //请求出错处理
                    layer.alert("请求失败！");
                }
            });
            return false;
        });
        form.on("submit(studentForm)", function (data) {
            var $ = layui.$;
            var username = $('#s_username').val();
            var password = $('#s_password').val();
            $.ajax({
                url: "/studentLogin",
                type: "post",
                traditional: true,
                data: {
                    "username": username,
                    "password": password
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    //请求成功时处理
                    if(data.status === 'success') {
                        window.location.href="/student/studentHome";
                    }else {
                        var msg = data.msg;
                        layer.alert(msg);
                    }
                }, error:function(){
                    //请求出错处理
                    layer.alert("请求失败！");
                }
            });
            return false;
        });
    });
</script>
</html>
