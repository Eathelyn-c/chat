<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" href="index.css">
<!DOCTYPE html>
<html>
<head>
    <title>JSP - 聊天室</title>
</head>
<body>
<h1><%= "用户登录" %>
</h1>
<br/>
<div class="form">
    <form action="login-servlet" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-actions">
            <a href="register.jsp">注册</a>
            <input type="submit" value="登录">
        </div>
    </form>
    <%
        // 显示错误信息
        String error = (String) session.getAttribute("error");
        if (error != null) {
    %>
    <div style="color: red;text-align: center"><%= error %></div>
    <%
            session.removeAttribute("error");
        }
    %>
</div>
</body>
</html>