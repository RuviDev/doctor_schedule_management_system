package com.ruvindu.doctorshiftmanagement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import com.ruvindu.doctorshiftmanagement.dao.ShiftUDDAO;
import com.ruvindu.doctorshiftmanagement.model.ShiftTable;
import com.ruvindu.doctorshiftmanagement.service.ShiftUpDel;
import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

@WebServlet("/UpdateShiftServlet")
public class UpdateShiftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShiftUpDel shiftUpDel;

    @Override
    public void init() {
        Connection conn = DatabaseConnection.getConnection();
        shiftUpDel = new ShiftUpDel(new ShiftUDDAO(conn));
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int shiftId = Integer.parseInt(request.getParameter("id"));
    	String dName = request.getParameter("udname");
        String shiftDate = request.getParameter("udate");
        String type = request.getParameter("utype");
        String startTime = request.getParameter("ustartTime");
        String endTime = request.getParameter("uendTime");
        String roomNumber = request.getParameter("uroomSelect");

        // Store the retrieved fields in a ShiftTable object
        ShiftTable shiftUpdate = new ShiftTable();
        shiftUpdate.setId(shiftId);
        shiftUpdate.setDoctorName(dName);
        shiftUpdate.setShiftDate(shiftDate);
        shiftUpdate.setShiftType(type);
        shiftUpdate.setStartTime(startTime);
        shiftUpdate.setEndTime(endTime);
        shiftUpdate.setRoomNumber(roomNumber);
        
     // Call the service method to update the shift and get the result message.
        String result = shiftUpDel.updateShift(shiftUpdate);
       
        request.getSession().setAttribute("messageU", result);
        response.sendRedirect("doctorShiftPage.jsp");
    }
}

