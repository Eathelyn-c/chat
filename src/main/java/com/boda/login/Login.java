//package com.boda.login;
//
//import java.io.*;
//import java.util.ArrayList;
//
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//@WebServlet("/login-servlet")
//public class Login extends HttpServlet {
//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response)
//            throws IOException {
//        response.setContentType("text/html");
//
//        HttpSession session = request.getSession();
//
//        // 从ServletContext获取用户信息
//        ServletContext context = getServletContext();
//        ArrayList<Users> users = (ArrayList<Users>) context.getAttribute("users");
//        if(users==null){
//            users=new ArrayList<>();
//            context.setAttribute("users",users);
//            //response.sendRedirect("index.jsp");
//        }
//
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        if(password == null || password.length() < 6){
//            session.setAttribute("error","密码长度不能小于6");
//            response.sendRedirect("index.jsp");
//            return;
//        }
//
//        String state=session.isNew()?"new":"ok";
//        String heading=null;
//        if(state.equals("new")) {
//            heading="欢迎您首次登录该页面！";
//        }
//        else {
//            heading="欢迎您再次回到页面！";
//        }
//        // 遍历所有用户，找到匹配的才成功
//        boolean loginSuccess = false;
//        for(Users user : users) {
//            if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
//                loginSuccess = true;
//                break; // 找到匹配用户，跳出循环
//            }
//        }
//
//        if(loginSuccess) {
//            // 登录成功
//            session.setAttribute("username", username);
//            session.setAttribute("password", password);
//            session.setAttribute("heading", heading); // 存储heading到session
//            session.removeAttribute("error"); // 清除可能的错误信息
//            //response.sendRedirect("chat.jsp");
//            response.sendRedirect("group-chat.jsp");
//        } else {
//            // 登录失败
//            session.setAttribute("error", "用户名或密码错误");
//            response.sendRedirect("index.jsp");
//        }
//    }
//    public void destroy() {
//    }
//}

package com.boda.login;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/login-servlet")
public class Login extends HttpServlet {
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        // 从ServletContext获取用户信息
        ServletContext context = getServletContext();
        ArrayList<Users> users = (ArrayList<Users>) context.getAttribute("users");
        if(users == null){
            users = new ArrayList<>();
            context.setAttribute("users", users);
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(password == null || password.length() < 6){
            session.setAttribute("error", "密码长度不能小于6");
            response.sendRedirect("index.jsp");
            return;
        }

        // 遍历所有用户，找到匹配的才成功
        boolean loginSuccess = false;
        for(Users user : users) {
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                loginSuccess = true;
                break;
            }
        }

        if(loginSuccess) {
            // 登录成功，添加到在线用户列表
            Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
            if(onlineUsers == null) {
                onlineUsers = new HashSet<>();
                context.setAttribute("onlineUsers", onlineUsers);
            }

            // 检查用户是否已经在线
            if(onlineUsers.contains(username)) {
                session.setAttribute("error", "该用户已在线");
                response.sendRedirect("index.jsp");
                return;
            }

            onlineUsers.add(username);
            context.setAttribute("onlineUsers", onlineUsers);
            System.out.println("用户 " + username + " 登录，当前在线用户: " + onlineUsers);

            // 设置session属性
            session.setAttribute("username", username);
            session.setAttribute("loggedIn", true);

            // 设置session超时时间（单位：秒）
            session.setMaxInactiveInterval(30 * 60); // 30分钟

            // 存储heading信息
            String heading = session.isNew() ? "欢迎您首次登录该页面！" : "欢迎您再次回到页面！";
            session.setAttribute("heading", heading);

            session.removeAttribute("error"); // 清除可能的错误信息
            response.sendRedirect("group-chat.jsp");
        } else {
            // 登录失败
            session.setAttribute("error", "用户名或密码错误");
            response.sendRedirect("index.jsp");
        }
    }

    public void destroy() {
    }
}