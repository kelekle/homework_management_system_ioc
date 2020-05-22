<%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/3/13
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kle.code.model.Teacher"%>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Teacher teacher = (Teacher)request.getAttribute("teacher");
    List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("list");

%>
<html>
<head>
    <title><%=teacher.getName() + "的个人主页"%></title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{margin: 10px;}
    </style>
</head>
<body>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHomework?tid=<%=teacher.getTid()%>"><img src="../images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<table id="demo" lay-filter="parse-table-demo">
    <thead>
    <tr>
        <th lay-data="{field: 'hid', width:100, sort: true, fixed: 'left'}">HID</th>
        <th lay-data="{field: 'sid', title: 'sid', width:100}"></th>
        <th lay-data="{field: 'title', title: '标题', width:140}"></th>
        <th lay-data="{field: 'content', title: '内容', width: 230}"></th>
        <th lay-data="{field: 'create_time', title: '创建时间', width:180}"></th>
        <th lay-data="{field: 'upload_time', title: '更新时间', width: 180}"></th>
        <th lay-data="{field: 'submit_content', title: '提交内容', width: 230}"></th>
        <th lay-data="{field: 'submit_time', title: '提交时间', width: 180}"></th>
    </tr>
    </thead>
    <tbody>
    <%
        for(Map<String, String> studentHomework : list){
    %>
    <tr>
        <td><%=studentHomework.get("hid")%></td>
        <td><%=studentHomework.get("sid")%></td>
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
<script src="../layui/layui.all.js"></script>
<script>
    layui.use(['table', 'jquery'], function(){
        var table = layui.table,
            $ = layui.$;
        table.init('parse-table-demo', { //转化静态表格
            //height: 'full-500'
            height: '500',
            page: true,
            // toolbar: '#toolbarDemo',//开启自定义工具行，指向自定义工具栏模板选择器
            defaultToolbar: ['filter', 'print', 'exports']
        });
    });
</script>

</body>
</html>
