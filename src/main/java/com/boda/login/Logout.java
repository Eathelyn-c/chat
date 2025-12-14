package com.boda.login;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

@WebServlet("/logout-servlet")
public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String username = (String) session.getAttribute("username");

            if (username != null) {
                // 从在线用户列表中移除
                ServletContext context = getServletContext();
                Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
                if (onlineUsers != null) {
                    onlineUsers.remove(username);
                    context.setAttribute("onlineUsers", onlineUsers);
                    System.out.println("用户 " + username + " 登出，当前在线用户: " + onlineUsers);
                }
            }

            // 使session无效
            session.invalidate();
        }

        response.sendRedirect("index.jsp");
    }
}