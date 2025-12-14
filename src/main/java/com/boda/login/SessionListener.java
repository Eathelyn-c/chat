package com.boda.login;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Session创建时不需要特别处理
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();

        // 当用户直接关闭浏览器或者会话超时，从在线用户列表中移除
        Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
        if (onlineUsers != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                onlineUsers.remove(username);
                context.setAttribute("onlineUsers", onlineUsers);
                System.out.println("用户 " + username + " 已下线");
            }
        }
    }
}