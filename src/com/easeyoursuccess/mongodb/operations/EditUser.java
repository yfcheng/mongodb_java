package com.easeyoursuccess.mongodb.operations;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easeyoursuccess.mongodb.dao.UserDAO;
import com.easeyoursuccess.mongodb.model.User;
import com.mongodb.MongoClient;

@WebServlet("/editUser")
public class EditUser extends HttpServlet {
   

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request,
           HttpServletResponse response) throws ServletException, IOException {
       String id = request.getParameter("id");
       if (id == null || "".equals(id)) {
           throw new ServletException("id missing for edit operation");
       }
       System.out.println("User edit requested with id=" + id);
       MongoClient mongo = (MongoClient) request.getServletContext()
               .getAttribute("MONGO_CLIENT");
       UserDAO userDAO = new UserDAO(mongo);
       User u = new User();
       u.setId(id);
       u = userDAO.readUser(u);
       request.setAttribute("user", u);
       List<User> users = userDAO.readAllUsers();
       request.setAttribute("users", users);

       RequestDispatcher reqDes = getServletContext().getRequestDispatcher(
               "/users.jsp");
       reqDes.forward(request, response);
   }

   protected void doPost(HttpServletRequest request,
           HttpServletResponse response) throws ServletException, IOException {
       String id = request.getParameter("id"); // keep it non-editable in UI
       if (id == null || "".equals(id)) {
           throw new ServletException("id missing for edit operation");
       }

       String name = request.getParameter("name");
       String email = request.getParameter("email");
       String password = request.getParameter("password");
       System.out.println("name from req is :"+name);
       System.out.println("Password from req is :"+password);

       if ((name == null || name.equals(""))
               || (email == null || email.equals(""))
               || (password == null || password.equals(""))) {
           request.setAttribute("error", "Name, Email Id & Password cannot be empty");
           MongoClient mongo = (MongoClient) request.getServletContext()
                   .getAttribute("MONGO_CLIENT");
           UserDAO userDAO = new UserDAO(mongo);
           User u = new User();
           u.setId(id);
           u.setName(name);
           u.setEmail(email);
           u.setPassword(password);
           request.setAttribute("user", u);
           List<User> users = userDAO.readAllUsers();
           request.setAttribute("users", users);

           RequestDispatcher reqDes = getServletContext().getRequestDispatcher(
                   "/users.jsp");
           reqDes.forward(request, response);
       } else {
           MongoClient mongo = (MongoClient) request.getServletContext()
                   .getAttribute("MONGO_CLIENT");
           UserDAO userDAO = new UserDAO(mongo);
           User u = new User();
           u.setId(id);
           u.setName(name);
           u.setEmail(email);
           u.setPassword(password);
           userDAO.updateUser(u);
           System.out.println("User edited successfully with id=" + id);
           request.setAttribute("success", "User edited successfully");
           List<User> users = userDAO.readAllUsers();
           request.setAttribute("users", users);

           RequestDispatcher reqDes = getServletContext().getRequestDispatcher(
                   "/users.jsp");
           reqDes.forward(request, response);
       }
   }

}