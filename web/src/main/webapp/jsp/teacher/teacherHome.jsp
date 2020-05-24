<%@ page import="com.kle.code.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kle.code.model.Teacher" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/9
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%
    Teacher teacher = (Teacher)request.getAttribute("teacher");
    List<Student> list = (List<Student>) request.getAttribute("student_list");
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
    <li class="layui-nav-item layui-this">
        <a href="${pageContext.request.contextPath}/teacher/teacherHome">我的学生</a>
    </li>
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHomework?tid=<%=((Teacher)request.getAttribute("teacher")).getTid()%>">我的作业</a>
    </li>
    <li class="layui-nav-item">
        <a href=""><img src="../images/default.png" class="layui-nav-img">我</a>
        <dl class="layui-nav-child" style="z-index: 9999">
            <dd style="z-index: 9999"><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
<table id="demo" lay-filter="parse-table-demo">
    <thead>
    <tr>
        <th lay-data="{field: 'sid', width:150, sort: true, fixed: 'left'}">SID</th>
        <th lay-data="{field: 'title', title: '名字', width:140}"></th>
        <th lay-data="{field: 'content', title: '密码', width: 230}"></th>
        <th lay-data="{field: 'create_time', title: '创建时间', width:180}"></th>
        <th lay-data="{field: 'upload_time', title: '更新时间', width: 180}"></th>
        <th lay-data="{fixed: 'right', align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
    <tbody>
    <%
        for(Student studentHomework:list){
    %>
    <tr>
        <td><%=String.valueOf(studentHomework.getSid())%></td>
        <td><%=studentHomework.getName()%></td>
        <td><%=studentHomework.getPassword()%></td>
        <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(studentHomework.getCreateTime())%></td>
        <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(studentHomework.getUpdateTime())%></td>
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
            toolbar: '#toolbarDemo',//开启自定义工具行，指向自定义工具栏模板选择器
            defaultToolbar: ['filter', 'print', 'exports']
        });
        //监听头工具栏事件
        table.on('toolbar(parse-table-demo)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    location.href="${pageContext.request.contextPath}/teacher/addStudent?tid=<%=String.valueOf(teacher.getTid())%>";
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(parse-table-demo)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'edit'){
                // layer.msg('编辑操作');
                location.href="/teacher/editStudent?sid=" + data.sid;
            }else if(layEvent === 'delete'){
                layer.confirm('确定删除吗?', {btn: ['确定删除', '取消']}, function (index) {
                    // var a1 = $("#ids").val(); //取得已获取的ids
                    $.ajax({
                        url: "${pageContext.request.contextPath }/teacher/deleteStudent",
                        type: "get",
                        traditional: true,
                        async: true,
                        data:{
                          "sid": data.sid
                        },
                        responseType: "json",
                        success:function(data){
                            //请求成功时处理
                            console.log(data);
                            layer.msg(data.msg);
                            if(data.status === 'success'){
                                layer.msg("成功删除！");
                                obj.del();
                                layer.close(index);
                            }else {
                                layer.alert("删除失败！");
                            }
                        },
                        error:function(){
                            //请求出错处理
                            layer.alert("请求失败！");
                        }
                    });
                })
            }
        });
    });
</script>

</body>
</html>
