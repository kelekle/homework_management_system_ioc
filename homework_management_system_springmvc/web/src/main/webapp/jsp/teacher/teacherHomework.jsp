<%@ page import="com.kle.code.model.Teacher" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kle.code.model.Homework" %><%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/10
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Teacher teacher = (Teacher)request.getAttribute("teacher");
    List<Homework> list = (List<Homework>) request.getAttribute("list");
%>
<html>
<head>
    <title><%=teacher.getName() + "的个人主页"%></title>
    <link rel="stylesheet" href="../../static/layui/css/layui.css">
    <style>
        body{margin: 10px;}
    </style>
</head>
<body>

<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/teacher/teacherHome">我的学生</a>
    </li>
    <li class="layui-nav-item layui-this">
        <a href="${pageContext.request.contextPath}/teacher/teacherHomework?tid="<%=((Teacher)request.getAttribute("teacher")).getTid()%>>我的作业</a>
    </li>
    <li class="layui-nav-item">
        <a href=""><img src="../../static/images/default.png" class="layui-nav-img">我</a>
        <dl class="layui-nav-child" style="z-index: 9999">
            <dd style="z-index: 9999"><a href="${pageContext.request.contextPath}/logout">退了</a></dd>
        </dl>
    </li>
</ul>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">布置作业</button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="query">完成情况</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<table id="demo" lay-filter="parse-table-demo">
    <thead>
    <tr>
        <th lay-data="{field: 'hid', width:150, sort: true, fixed: 'left'}">HID</th>
        <th lay-data="{field: 'title', title: '标题', width:140}"></th>
        <th lay-data="{field: 'content', title: '内容', width: 230}"></th>
        <th lay-data="{field: 'create_time', title: '创建时间', width:180}"></th>
        <th lay-data="{field: 'upload_time', title: '更新时间', width: 180}"></th>
        <th lay-data="{fixed: 'right', align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
    <tbody>
    <%
        for(Homework studentHomework:list){
    %>
    <tr>
        <td><%=studentHomework.getHid()%></td>
        <td><%=studentHomework.getTitle()%></td>
        <td><%=studentHomework.getContent()%></td>
        <td><%=studentHomework.getCreateTime()%></td>
        <td><%=studentHomework.getUpdateTime()%></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<script src="../../static/layui/layui.all.js"></script>
<script>
    layui.use(['jquery','table'], function(){
        var table = layui.table,
        $ = layui.$;
        table.init('parse-table-demo', { //转化静态表格
            //height: 'full-500'
            height: 500,
            page: true,
            toolbar: '#toolbarDemo',//开启自定义工具行，指向自定义工具栏模板选择器
            defaultToolbar: ['filter', 'print', 'exports']
        });
        table.on('toolbar(parse-table-demo)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    location.href="${pageContext.request.contextPath}/teacher/addHomework?tid=<%=teacher.getTid()%>";
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(parse-table-demo)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'edit'){
                // layer.msg('编辑操作');
                location.href="/teacher/editHomework?hid=" + data.hid + "&tid=<%=teacher.getTid()%>";
            }else if(layEvent === 'delete'){
                layer.confirm('确定删除吗?', {btn: ['确定删除', '取消']}, function (index) {
                    var a1 = $("#ids").val(); //取得已获取的ids
                    $.ajax({
                        url: "/teacher/deleteHomework",
                        type: "get",
                        data:{
                            "hid": data.hid
                        },
                        responseType: "json ",
                        success:function(data){
                            console.log(data);
                            //请求成功时处理
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
            }else if(layEvent === 'query'){
                location.href="/teacher/studentHomework?hid=" + data.hid + "&tid=<%=teacher.getTid()%>";
            }
        });
    });
</script>

</body>
</html>
