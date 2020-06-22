
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台登录页面</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/media/layui/css/layui.css">

    <style type="text/css">
        .form-out{
            margin-top: 150px;
        }
        .form-center{
            margin-left:380px;
        }
        .title-h2{
            text-align: center;
            padding:30px;
            color: #0C0C0C;
        }
        .form-message{
            text-indent: 110px;
            color: #eb7350;
        }
    </style>
</head>
<body class="layui-bg-gray">
<div class="layui-container">
    <div class="layui-row form-out" >
        <div class="layui-col-md4 form-center" >
            <form class="layui-form" action="<%=request.getContextPath()%>/dologin.html" method="post">
                <div class="layui-form-item">
                    <h2 class="title-h2">考试管理系统后台登录</h2>
                    <label class="layui-form-label">用户名：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="account" required lay-verify="required" placeholder="请输入用户名"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" required lay-verify="required" placeholder="请输入密码"
                               autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid form-message">${message}</div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formDemo">立即登录</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
