<%@ page import="com.kle.code.model.Homework" %><%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/13
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String tid = (String) request.getAttribute("tid");
    Homework homework = (Homework) request.getAttribute("homework");
%>
<html>
<head>
    <title>编辑作业</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{margin: 10px;}
        .homework{ height:300px;width:550px; border-style: solid; border-color: #9F9F9F; border-width:1px; border-radius: 4px; padding: 20px; position:absolute;left: 50%; top: 15%; margin:0 0 0 -225px;}
    </style>
</head>
<body>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHomework?tid=<%=tid%>"><img src="../images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<div class="homework">
    <h2 style="font-weight: bold; text-align: center">修改作业</h2>
<form class="layui-form layui-form-pane" style="height: 230px; margin-top: 10px;" action="" method="post">
    <div class="layui-form-item">
        <label class="layui-form-label">作业标题</label>
        <input class="layui-input" placeholder="请输入标题" value="<%=homework.getTitle()%>" id="title" type="text" name="id" required />
    </div>
    <div class="layui-form-item layui-form-text" style="">
        <label class="layui-form-label">作业内容</label>
        <div class="layui-input-block" >
            <textarea id="content" placeholder="请输入内容" required name="submit_content" class="layui-textarea"><%=homework.getContent()%></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <button type="button" class="layui-btn" data-type="getid">完成</button>
    </div>
</form>
</div>
<script src="../layui/layui.all.js"></script>
<script>
    layui.use(['jquery','table'], function(){
        var table = layui.table,
            $ = layui.$;
        table.init('parse-table-demo', { //转化静态表格
            //height: 'full-500'
            height: 300,
            page: true,
            toolbar: '#toolbarDemo',//开启自定义工具行，指向自定义工具栏模板选择器
            defaultToolbar: ['filter', 'print', 'exports']
        });
        table.on('checkbox(table)', function(obj){
            var data = obj.data;
        });
        $('.layui-btn').on('click', function(){
            var title = $('#title').val();
            var content = $('#content').val();
            console.log(title);
            console.log(content);
            if(isnull(title)){
                layer.msg("标题不能为空！");
                return;
            }
            if(isnull(content)){
                layer.msg("内容不能为空！");
                return;
            }
            $.ajax({
                url: "/teacher/homework",
                type: "post",
                traditional: true,
                data: {
                    "hid": <%=homework.getHid()%>,
                    "title": title,
                    "content": content
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    //请求成功时处理
                    if(data.status === 'success') {
                        layer.msg("成功修改作业！");
                        setTimeout("location.href='/teacher/teacherHomework?tid=<%=tid%>'", 1000);
                    }else {
                        layer.alert("修改作业失败！");
                    }
                }, error:function(){
                    //请求出错处理
                    layer.alert("请求失败！");
                }
            });
        });
    });
    function isnull(val) {
        var str = val.replace(/(^\s*)|(\s*$)/g, '');//去除空格;
        return str === '' || str === undefined || str == null;
    }
</script>
</body>
</html>
