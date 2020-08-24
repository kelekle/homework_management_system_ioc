<%@ page import="com.kle.code.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/9
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Student student = ((Student)request.getAttribute("student"));
    List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("list");
%>
<html>
<head>
    <title><%=student.getName() + "的个人主页"%></title>
    <link rel="stylesheet" href="../../static/layui/css/layui.css">
    <style>
        body{margin: 10px;}
    </style>
</head>
<body>

<ul class="layui-nav">
    <li class="layui-nav-item layui-this">
        <a href="">我的作业</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../../static/images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child" style="z-index: 9999">
            <dd style="z-index: 9999"><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<script type="text/html" id="barDemo">
<%--    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
    <a class="layui-btn layui-btn-xs" lay-event="edit">提交</a>
</script>
<%--<div style="width:1360px; height: 500px;position: absolute; left: 50%; margin-left: -690px; margin-top: 30px">--%>
<%--<div style="height: 1000px">--%>
<table id="demo" lay-filter="parse-table-demo">
    <thead>
    <tr>
        <th lay-data="{field: 'hid', width:100, sort: true, fixed: 'left'}">HID</th>
        <th lay-data="{field: 'title', title: '标题', width:140}"></th>
        <th lay-data="{field: 'content', title: '内容', width: 230}"></th>
        <th lay-data="{field: 'create_time', title: '创建时间', width:180}"></th>
        <th lay-data="{field: 'upload_time', title: '更新时间', width: 180}"></th>
        <th lay-data="{field: 'submit_content', title: '提交内容', width: 230}"></th>
        <th lay-data="{field: 'submit_time', title: '提交时间', width: 180}"></th>
        <th lay-data="{fixed: 'right', align: 'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
    <tbody>
    <%
        for(Map<String, String> studentHomework:list){
    %>
    <tr>
        <td><%=studentHomework.get("hid")%></td>
        <td><%=studentHomework.get("title")%></td>
        <td><%=studentHomework.get("content")%></td>
        <td><%=studentHomework.get("create_time")%></td>
        <td><%=studentHomework.get("update_time")%></td>
        <%
            if("".equals(studentHomework.get("submit_content"))){
        %>
                <td>未提交</td>
        <%
            }else {
        %>
                <td><%=studentHomework.get("submit_content")%></td>
        <%
            }
        %>
        <%
            if("".equals(studentHomework.get("submit_time"))){
        %>
                <td>未提交</td>
        <%
            }else {
        %>
            <td><%=studentHomework.get("submit_time")%></td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%--</div>--%>
<script src="../../static/layui/layui.all.js"></script>
<script>
    layui.use('table', function(){
        var table = layui.table;
        table.init('parse-table-demo', { //转化静态表格
            height: 500,
            page: true //开启分页
        });

        //监听行工具事件
        table.on('tool(parse-table-demo)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'edit'){
                location.href="/student/submitHomework?hid=" + data.hid + "&sid=<%=student.getSid()%>";
            }
        });
    });
</script>

</body>
</html>
