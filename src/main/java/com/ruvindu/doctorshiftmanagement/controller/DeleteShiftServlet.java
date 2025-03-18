package com.ruvindu.doctorshiftmanagement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import com.ruvindu.doctorshiftmanagement.dao.ShiftUDDAO;
import com.ruvindu.doctorshiftmanagement.service.ShiftUpDel;
import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

@WebServlet("/DeleteShiftServlet")
public class DeleteShiftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShiftUpDel shiftUpDel;

	// Initialize the ShiftUpDek and set up a database connection when the servlet is first loaded.
    @Override
    public void init() {
        Connection conn = DatabaseConnection.getConnection();
        shiftUpDel = new ShiftUpDel(new ShiftUDDAO(conn));
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int shiftId = Integer.parseInt(request.getParameter("id"));
        String shiftType = request.getParameter("type");

     // Call the service method to delete the shift and get the result message.
        String result = shiftUpDel.deleteShiftById(shiftId, shiftType);

        request.getSession().setAttribute("messageU", result);
        response.sendRedirect("doctorShiftPage.jsp");
    }
}
