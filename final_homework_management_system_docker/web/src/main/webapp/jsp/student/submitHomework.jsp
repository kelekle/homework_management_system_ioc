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
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{overflow: hidden}
        .homework{ height:415px;width:800px;padding: 20px; border-style: solid; border-width: 1px; border-color:#9F9F9F; border-radius: 4px;position:absolute;left: 50%; top: 15%; margin:0 0 0 -400px;}
        .homework h1{ height:30px; word-wrap: break-word; overflow: hidden; text-align:left; color:#fff; font-size:20px; }
        .homework h4{ word-wrap: break-word; overflow: hidden; text-align:left; color:#fff; font-size:16px; margin-top: 10px;}
    </style>
</head>
<body>
<%Map<String, String> map = (Map<String, String>) request.getAttribute("studentHomework");%>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/student/studentHome"><img src="../images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<div class="homework">
    <div style="height: 210px">
        <h1 style="font-weight: bold; text-align: center;color: #0C0C0C">提交作业</h1>
        <h3 style="margin-top: 10px">作业题目：&nbsp;&nbsp;<%=map.get("title")%></h3>
        <h4 style="max-height: 130px; height: 20px; color: #0C0C0C">作业内容：&nbsp;&nbsp;<%=map.get("content")%></h4>
        <h4 style="height: 20px; color: #0C0C0C">创建时间：&nbsp;&nbsp;<%=map.get("create_time")%></h4>
        <h4 style="height: 20px; color: #0C0C0C">更新时间：&nbsp;&nbsp;<%=map.get("update_time")%></h4>
    </div>
    <form class="layui-form layui-form-pane" style="height: 200px; margin-top: 10px;" method="post">
        <div class="layui-form-item layui-form-text" style="">
            <label class="layui-form-label">作答区</label>
            <div class="layui-input-block" >
                <% if(!"未提交".equals(map.get("submit_content"))){%>
                <textarea placeholder="请输入内容" required id="submit_content" name="submit_content" class="layui-textarea"><%=map.get("submit_content")%></textarea>
                <%}else{%>
                <textarea placeholder="请输入内容" required id="submit_content" name="submit_content" class="layui-textarea"></textarea>
                <%}
                %>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="demo2">提交</button>
        </div>
    </form>
</div>

<script src="../layui/layui.all.js"></script>
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
        form.on("submit(demo2)", function (data) {
            var $ = layui.$;
            var content = $('#submit_content').val();
            if(isnull(content)){
                layer.msg("内容不能为空！");
                return;
            }
            console.log(content);
            $.ajax({
                url: "/student/homework",
                type: "post",
                traditional: true,
                data: {
                    "submit_content": content,
                    "sid": <%=map.get("sid")%>,
                    "hid": <%=map.get("hid")%>
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    // 请求成功时处理
                    if(data.status === 'success') {
                        layer.msg("提交成功！");
                        setTimeout("location.href='/student/studentHome'", 1000);
                    }else {
                        layer.alert("提交失败，请重新提交！");
                    }
                }, error:function(){
                    //请求出错处理
                    layer.alert("请求失败！");
                }
            });
            return false;
        });
    });
    function isnull(val) {
        var str = val.replace(/(^\s*)|(\s*$)/g, '');//去除空格;
        return str === '' || str === undefined || str == null;
    }
</script>

</body>
</html>
