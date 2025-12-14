package com.boda.login;

import jakarta.servlet.RequestDispatcher;
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
import java.util.HashSet;

@WebServlet("/chat-servlet")
public class chat extends HttpServlet {
   public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
           throws IOException, ServletException {
       response.setContentType("text/html");

       HttpSession session = request.getSession();
       String username1 = (String) session.getAttribute("username");
       String username2 = request.getParameter("username2");
       String content = request.getParameter("message");

       if(username1!=null&&username2!=null&&username2!=username1){

           ServletContext message = getServletContext();

          ArrayList<Message> messages=(ArrayList<Message>) message.getAttribute("messages");
           // 如果消息列表不存在，创建新的
           if (messages == null) {
               messages = new ArrayList<>();
           }
           messages.add(new Message(username1,username2,content));

           message.setAttribute("messages",messages);
       }
       //RequestDispatcher rd = request.getRequestDispatcher("chat.jsp");
       //rd.forward(request, response);
       // 重定向回聊天页面，保持选择的用户
       response.sendRedirect("chat.jsp?username2=" + URLEncoder.encode(username2, "UTF-8"));
   }
}
