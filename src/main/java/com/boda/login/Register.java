package com.boda.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/register-servlet")
public class Register extends HttpServlet {
    public void doPost(HttpServletRequest request,
                HttpServletResponse response)
            throws IOException , ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        RequestDispatcher rd = null;
        if(!password.equals(confirm)){
            request.setAttribute("message","密码不一致");
            rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request,response);
            return;
        }
        if(password.length()<6){
            request.setAttribute("message","密码长度不能小于6");
            rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request,response);
            return;
        }
        // 从ServletContext获取用户列表
        ServletContext context = getServletContext();
        ArrayList<Users> users = (ArrayList<Users>) context.getAttribute("users");
        if(users == null) {
            users = new ArrayList<>();
        }

        for(Users user:users) {
            if (user.getUsername().equals(username)) {
                request.setAttribute("message", "用户已存在");
                rd=request.getRequestDispatcher("register.jsp");
                rd.forward(request, response);
                return;
            }
        }
        users.add(new Users(username,password));
        context.setAttribute("users",users);

        // 将注册结果保存在 request 中，使用 forward 使 JSP 可以看到这些属性并写入 localStorage
        request.setAttribute("message","注册成功");
        request.setAttribute("username", username);
        request.setAttribute("success", Boolean.TRUE);
        rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

}
