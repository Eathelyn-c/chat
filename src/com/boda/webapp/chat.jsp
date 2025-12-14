<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.boda.login.Message" %>
<%@ page import="com.boda.login.Users" %>
<link rel="stylesheet" href="chat.css">

<html>
<head>
   <title>聊天室</title>
</head>
<body>
<%
   String heading = (String) session.getAttribute("heading");
   String username = (String) session.getAttribute("username");
   if (username == null) {
       response.sendRedirect("index.jsp");
       return;
   }

   // 获取当前选中的用户
   String username2selected = request.getParameter("username2");

   // 获取所有消息
   ArrayList<Message> allMessages = (ArrayList<Message>) application.getAttribute("messages");
   if (allMessages == null) {
       allMessages = new ArrayList<>();
   }

   // 获取所有用户
   ArrayList<Users> users = (ArrayList<Users>) application.getAttribute("users");
%>

<h1><%= heading != null ? heading : "欢迎来到聊天室" %></h1>
<p>当前用户: <strong><%= username %></strong></p>
<a href="index.jsp">退出登录</a>

<h2>聊天记录:</h2>
<div id="chat-history" class="chat-history">
   <%
       // 如果没有选择用户或没有消息，显示提示
       if (username2selected == null || username2selected.trim().isEmpty()) {
   %>
   <p>请选择一个用户开始聊天</p>
   <%
   } else {
       // 显示与选中用户的聊天记录
       boolean hasMessages = false;
       for (Message mes : allMessages) {
           // 检查是否是当前对话的消息（双向）
           boolean isCurrentConversation =
                   (mes.getUsername1().equals(username) && mes.getUsername2().equals(username2selected)) ||
                           (mes.getUsername1().equals(username2selected) && mes.getUsername2().equals(username));

           if (isCurrentConversation) {
               hasMessages = true;
               boolean isSentByMe = mes.getUsername1().equals(username);
   %>
   <div class="message <%= isSentByMe ? "sent" : "received" %>">
       <strong><%= mes.getUsername1() %>:</strong> <%= mes.getContent() %>
   </div>
   <%
           }
       }

       if (!hasMessages) {
   %>
   <p>还没有与 <%= username2selected %> 的聊天记录，开始对话吧！</p>
   <%
           }
       }
   %>
</div>

<form action="chat-servlet" method="post" id="chat-form">
   <h2>请选择用户:</h2>
   <select name="username2" id="user-selector">
       <option value="">-- 请选择用户 --</option>
       <%
           if (users != null) {
               for (Users user : users) {
                   if (!user.getUsername().equals(username)) {
                       String selected = "";
                       if (username2selected != null && username2selected.equals(user.getUsername())) {
                           selected = "selected";
                       }
       %>
       <option value="<%= user.getUsername() %>" <%= selected %>><%= user.getUsername() %></option>
       <%
                   }
               }
           }
       %>
   </select>

   <h2>发送消息:</h2>
   <textarea name="message" rows="4" cols="50" id="message-input"></textarea>
   <br>
   <input type="submit" value="发送">
</form>
<script src="chat.js"></script>
</body>
</html>
