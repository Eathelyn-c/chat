<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="register.css">
<html>
<head>
   <title>JSP - 注册</title>
</head>
<body>
<h1>注册</h1>
<div class="container">
<form action="register-servlet" method="post">
   <div class="form-group">
       <label>用户名：</label>
       <input type="text" name="username" required><br>
   </div>
   <div class="form-group">
       <label>密码：</label>
       <input type="password" name="password" required><br>
   </div>
   <div class="form-group">
       <label>确认密码：</label>
       <input type="password" name="confirmPassword" required><br>
   </div>
   <div class="from-action"><input type="submit" value="注册"><!--如果注册成功返回登录--></div>
</form>
<%
   // 显示信息
   String message = (String) request.getAttribute("message");
   Boolean success = (Boolean) request.getAttribute("success");
   String username = (String) request.getAttribute("username");
   if (message != null) {
%>
<div style="color: black;"><%= message %></div>
<%
   }
%>
</div>
</body>
</html>
