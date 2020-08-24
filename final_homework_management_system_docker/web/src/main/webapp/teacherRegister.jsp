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
        .title{
            font-size: 20px;
            margin: 10px auto;
        }
        /*.layui-form-label{*/
        /*  !*margin-left: -40px;*!*/
        /*}*/
    </style>
</head>
<body>

<div class="register" lay-filter="demo">
    <div class="title">教师注册</div>
    <form class="layui-form" method="post">
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
                    <input id="t_password" type="password" name="t_password" lay-verify="required|pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <button class="layui-btn" lay-submit="" lay-filter="formDemo">注册</button>
            </div>
        </div>
    </form>
</div>
<script src="layui/layui.all.js"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form;
        //要放在form.on外面，千万不能放在提交步骤中，否则会不触发
        form.verify({
            pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
        });
        form.on("submit(formDemo)", function (data) {
            var $ = layui.$;
            var name = $('#t_name').val();
            var password = $('#t_password').val();
            console.log(name);
            console.log(password);
            $.ajax({
                url: "/teacherRegister",
                type: "post",
                traditional: true,
                data: {
                    "t_name": name,
                    "t_password": password
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    if(data.status === 'success') {
                        layer.msg("注册成功！")
                        location.href="/register-success?id=" + data.id;
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
</body>
</html>