<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="chat.css">
<script src="chat.js"></script>
<!DOCTYPE html>
<html>
<head>
    <title>私聊室</title>
</head>
<body onload="initPage()">
<!-- 获取当前选中的私聊用户 -->
<c:set var="username2selected" value="${param.username2}" />

<!-- 获取应用数据 -->
<c:set var="allMessages" value="${applicationScope.messages}" />
<c:if test="${empty allMessages}">
    <c:set var="allMessages" value="${[]}" scope="page" />
</c:if>

<c:set var="users" value="${applicationScope.users}" />
<c:set var="onlineUsers" value="${applicationScope.onlineUsers}" />
<c:set var="onlineCount" value="${not empty onlineUsers ? onlineUsers.size() : 0}" />
<c:set var="currentUser" value="${sessionScope.username}" />

<h1>私聊室</h1>
<div class="user-info">
    <p>当前用户: <strong>${currentUser}</strong> |
        在线用户: <strong>${onlineCount}</strong> 人</p>
</div>
<%--聊天模式切换表单--%>
<form id="switchForm" action="chat-servlet" method="get">
    <input type="hidden" name="action" value="switchMode" />
    <div class="chat-mode-selector">
        <label for="chatTarget">切换到:</label>
        <select name="chatTarget" id="chatTarget" onchange="this.form.submit()">
            <option value="group">群聊（所有人）</option>
            <c:if test="${not empty users}">
                <c:forEach var="user" items="${users}">
                    <c:if test="${user.username ne currentUser}">
                        <option value="${user.username}"
                            ${username2selected eq user.username ? 'selected' : ''}>
                                ${user.username}
                            <c:choose>
                                <c:when test="${onlineUsers.contains(user.username)}">
                                    ● 在线
                                </c:when>
                                <c:otherwise>
                                    ○ 离线
                                </c:otherwise>
                            </c:choose>
                        </option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>
    </div>
</form>

<div class="navigation">
    <a href="logout-servlet">退出登录</a>
</div>
    <div class="chat-area">
        <c:choose>
            <c:when test="${not empty username2selected}">
                <!-- 检查目标用户是否在线 -->
                <c:set var="targetUserOnline"
                       value="${onlineUsers.contains(username2selected)}" />

                <c:if test="${not targetUserOnline}">
                    <div class="warning">
                        <h2>用户 ${username2selected} 当前不在线</h2>
                        <p>您可以发送消息，但对方需要上线后才能看到</p>
                    </div>
                </c:if>

                <h2>与 <strong>${username2selected}</strong> 的私聊记录:</h2>
                <div id="chat-history" class="chat-history private-chat">
                    <c:set var="hasPrivateMessages" value="false" />
                    <c:forEach var="mes" items="${allMessages}">
                        <c:if test="${not mes.broadcast}">
                            <c:if test="${(mes.username1 eq currentUser and mes.username2 eq username2selected) or
                                         (mes.username1 eq username2selected and mes.username2 eq currentUser)}">
                                <c:set var="hasPrivateMessages" value="true" />
                                <div class="message ${mes.username1 eq currentUser ? 'sent' : 'received'}">
                                    <div class="message-header">
                                        <strong>${mes.username1}:</strong>
                                        <span class="timestamp">${mes.timestamp}</span>
                                    </div>
                                    <div class="message-content">${mes.content}</div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test="${not hasPrivateMessages}">
                        <p>还没有与 ${username2selected} 的聊天记录，开始对话吧！</p>
                    </c:if>
                </div>

                <form action="chat-servlet" method="post" class="private-chat-form">
                    <input type="hidden" name="chatType" value="private">
                    <input type="hidden" name="username2" value="${username2selected}">

                    <h3>发送私聊消息给 ${username2selected}:</h3>
                    <textarea name="message" rows="4" cols="50" id="message-input" required
                              placeholder="发送给 ${username2selected} 的私聊消息..."></textarea>
                    <br>
                    <input type="submit" value="发送私聊消息">
                </form>
            </c:when>
            <c:otherwise>
                <div class="no-user-selected">
                    <h2>请选择聊天用户</h2>
                    <p>请从上方的下拉框中选择一个用户开始私聊</p>
                    <p>私聊消息只有您和对方可见</p>
                    <p><strong>提示：</strong>只能与在线用户进行实时私聊</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>