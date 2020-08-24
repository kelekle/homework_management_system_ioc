<%@ page import="com.kle.code.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: ypb
  Date: 2020/6/13
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String tid = (String) request.getAttribute("tid");
    List<Student> list = (List<Student>) request.getAttribute("student_list");
%>
<html>
<head>
    <title>添加我的学生</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{margin: 10px;}
    </style>
</head>
<body>

<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHome?tid=<%=tid%>"><img src="../images/back.png" class="layui-nav-img" alt="">返回</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;"><img src="../images/default.png" class="layui-nav-img" alt="">我</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<table lay-data="{id:'demo'}" lay-filter="parse-table-demo">
    <thead>
    <tr>
        <th lay-data="{type: 'checkbox', fixed: 'left'}"></th>
        <th lay-data="{field: 'sid', width:180, sort: true, fixed: 'left'}">SID</th>
        <th lay-data="{field: 'title', title: '名字', width:200}"></th>
        <th lay-data="{field: 'create_time', title: '创建时间', width:230}"></th>
        <th lay-data="{field: 'upload_time', title: '更新时间', width: 230}"></th>
        <th lay-data="{fixed: 'right', align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
    <tbody>
    <%
        for(Student student : list){
    %>
    <tr>
        <td><%=student.getSid()%></td>
        <td><%=student.getSid()%></td>
        <td><%=student.getName()%></td>
        <%--        <td><%=studentHomework.getPassword()%></td>--%>
        <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student.getCreateTime())%></td>
        <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student.getUpdateTime())%></td>
    </tr>
    <%
        }
    %>
    </tbody>

</table>

<form class="layui-form layui-form-pane" style="height: 200px; margin-top: 10px;" action="" method="post">
    <div class="layui-form-item">
        <button type="button" class="layui-btn" data-type="getid">添加</button>
    </div>
</form>

<script src="../layui/layui.all.js"></script>
<script>
    layui.use(['jquery','table'], function(){
        var table = layui.table,
            $ = layui.$;
        table.init('parse-table-demo', { //转化静态表格
            // height: 'full-500',
            height: 500,
            page: true,
            toolbar: '#toolbarDemo',//开启自定义工具行，指向自定义工具栏模板选择器
            defaultToolbar: ['filter', 'print', 'exports']
        });
        table.on('checkbox(table)', function(obj){
            var data = obj.data;
        });
        $('.layui-btn').on('click', function(){
            var arr = [];
            var checkStatus = table.checkStatus('demo')
                , data = checkStatus.data;
            for (var i = 0; i < data.length; i++) {    //循环筛选出id
                arr.push(data[i].sid);
            }
            console.log(arr);
            if(arr.length <= 0){
                layer.msg("请至少选中一项！");
                return;
            }
            $.ajax({
                url: "/teacher/new-student",
                type: "post",
                traditional: true,
                data: {
                    "tid": <%=tid%>,
                    "idList": arr
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    //请求成功时处理
                    if(data.status === 'success') {
                        layer.msg("添加成功,1s后自动跳转至首页！");
                        setTimeout("location.href='/teacher/teacherHome?tid=<%=tid%>'", 1000);
                    }else {
                        layer.alert("添加失败！");
                    }
                }, error:function(){
                    //请求出错处理
                    layer.alert("请求失败！");
                }
            });
        });
    });
</script>
</body>
</html>
