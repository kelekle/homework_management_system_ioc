<%@ page import="com.kle.code.model.Student" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 风在野
  Date: 2020/3/10
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String tid = (String) request.getAttribute("tid");
%>
<html>
<head>
    <title>布置作业</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <style>
        body{margin: 10px;}
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

<form class="layui-form layui-form-pane" style="height: 200px; margin-top: 10px;" action="" method="post">
    <div class="layui-form-item">
        <label class="layui-form-label">作业标题</label>
        <input width="100%" id="title" placeholder="请输入标题" type="text" name="id" required class="layui-input"/>
    </div>
    <div class="layui-form-item layui-form-text" style="">
        <label class="layui-form-label">作业内容</label>
        <div class="layui-input-block" >
            <textarea id="content" placeholder="请输入内容" required name="submit_content" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <button type="button" class="layui-btn" data-type="getid">发布</button>
    </div>
</form>

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
        // var active = {
        //     getid: function () {
        //         var arr = [];
        //         var checkStatus = table.checkStatus('demo')
        //             , data = checkStatus.data;
        //         for (var i = 0; i < data.length; i++) {    //循环筛选出id
        //             arr.push(data[i].sid);
        //         }
        //         $("#ids").val(arr);
        //     }
        // };
        $('.layui-btn').on('click', function(){
            // var type = $(this).data('type');
            // active[type] ? active[type].call(this) : '';
            // var arr = $("#ids").val(); //取得已获取的ids
            var title = $('#title').val();
            var content = $('#content').val();
            console.log(title);
            console.log(content);
            console.log("addHomework");
            if(isnull(title)){
                layer.msg("标题不能为空！");
                return;
            }
            if(isnull(content)){
                layer.msg("内容不能为空！");
                return;
            }
            $.ajax({
                url: "/teacher/new-homework",
                type: "post",
                traditional: true,
                data: {
                    "tid": <%=tid%>,
                    "title": title,
                    "content": content
                },
                responseType: "json",
                success:function(data){
                    console.log(data);
                    //请求成功时处理
                    if(data.status === 'success') {
                        layer.msg("成功布置作业！");
                        setTimeout("location.href='/teacher/teacherHomework?tid=<%=tid%>'", 1200);
                    }else {
                        layer.alert("布置作业失败！");
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