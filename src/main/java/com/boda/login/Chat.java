package com.boda.login;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;

@WebServlet("/chat-servlet")
public class Chat extends HttpServlet {

    // 处理消息发送
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String username1 = (String) session.getAttribute("username");
        String username2 = request.getParameter("username2");
        String content = request.getParameter("message");
        String chatType = request.getParameter("chatType");

        // 检查消息是否为空
        if(content == null || content.trim().isEmpty()) {
            // 根据聊天类型重定向到相应页面
            if ("private".equals(chatType) && username2 != null && !username2.trim().isEmpty()) {
                response.sendRedirect("private-chat.jsp?username2=" +
                        URLEncoder.encode(username2, "UTF-8"));
            } else {
                response.sendRedirect("group-chat.jsp");
            }
            return;
        }

        ServletContext context = getServletContext();
        ArrayList<Message> messages = (ArrayList<Message>) context.getAttribute("messages");
        // 如果消息列表不存在，创建新的
        if (messages == null) {
            messages = new ArrayList<>();
        }

        // 根据聊天类型存储消息
        if ("private".equals(chatType) && username2 != null && !username2.trim().isEmpty() && !username2.equals(username1)) {
            // 私聊消息
            messages.add(new Message(username1, username2, content, false));
            context.setAttribute("messages", messages);
            response.sendRedirect("private-chat.jsp?username2=" +
                    URLEncoder.encode(username2, "UTF-8"));
        } else if ("group".equals(chatType)) {
            // 检查在线用户数量
            Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
            if(onlineUsers == null || onlineUsers.size() < 2) {
                // 只有一个人在线，不允许群聊
                session.setAttribute("error", "群聊需要至少两个在线用户");
                response.sendRedirect("group-chat.jsp");
                return;
            }

            // 群发消息
            messages.add(new Message(username1, "ALL", content, true));
            context.setAttribute("messages", messages);
            response.sendRedirect("group-chat.jsp");
        }
    }

    // 处理聊天模式切换
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String chatTarget = request.getParameter("chatTarget");

        if ("group".equals(chatTarget)) {
            // 切换到群聊
            response.sendRedirect("group-chat.jsp");
        } else if (chatTarget != null && !chatTarget.trim().isEmpty()) {
            // 切换到私聊，chatTarget就是私聊用户名
            response.sendRedirect("private-chat.jsp?username2=" +
                    URLEncoder.encode(chatTarget, "UTF-8"));
        } else {
            // 默认跳转到群聊
            response.sendRedirect("group-chat.jsp");
        }
    }
}