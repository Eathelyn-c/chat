package com.boda.login;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")  // 添加 URL 模式，过滤所有请求
public class Filter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 获取当前请求的路径
        String path = req.getRequestURI().substring(req.getContextPath().length());

        System.out.println("过滤器拦截路径: " + path);

        // 不需要检查登录的路径
        if (path.equals("/") ||
                path.equals("/index.jsp") ||
                path.equals("/register.jsp") ||
                path.startsWith("/login-servlet") ||
                path.startsWith("/register-servlet") ||
                path.startsWith("/logout-servlet") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".gif") ||
                path.endsWith(".ico")) {

            System.out.println("放行路径: " + path); // 调试用
            chain.doFilter(request, response);
            return;
        }

        // 检查session中是否有username
        HttpSession session = req.getSession(false);

        // 如果没有登录，重定向到登录页面
        if (session == null || session.getAttribute("username") == null) {
            System.out.println("未登录，重定向到登录页");
            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // 已登录，继续处理请求
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码
    }

    @Override
    public void destroy() {
        // 清理代码
    }
}