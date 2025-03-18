package com.ruvindu.doctorshiftmanagement.controller;

import java.io.IOException;

import com.ruvindu.doctorshiftmanagement.dao.LoginDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginDAO loginDAO = new LoginDAO();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (loginDAO.check(username, password)) {
            // If the Authentication is successful, create session and redirect to doctorShiftPage
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("doctorShiftPage.jsp");
            
        } else {
            // If the Authentication failed, redirect back to login with error message
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
