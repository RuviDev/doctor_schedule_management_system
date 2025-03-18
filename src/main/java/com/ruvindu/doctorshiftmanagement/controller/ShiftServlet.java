package com.ruvindu.doctorshiftmanagement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import com.ruvindu.doctorshiftmanagement.dao.*;
import com.ruvindu.doctorshiftmanagement.model.*;
import com.ruvindu.doctorshiftmanagement.service.*;
import com.ruvindu.doctorshiftmanagement.util.*;

@WebServlet("/ShiftServlet")
public class ShiftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShiftService shiftService;

	// Initialize the ShiftService and set up a database connection when the ervlet is first loaded.
    @Override
    public void init() {
        Connection conn = DatabaseConnection.getConnection();
        shiftService = new ShiftService(new ShiftDAOImpl(conn));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shiftType = request.getParameter("shiftType");
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        String shiftDate = request.getParameter("shiftDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int room = Integer.parseInt(request.getParameter("roomSelect"));

     // Create a shift object based on the shift type.
        Shift shift;
        if (shiftType.equals("Morning")) {
            shift = new MorningShift();
        } else if (shiftType.equals("Evening")) {
            shift = new EveningShift();
        } else {
            shift = new NightShift();
        }
        
     // Set the shift details in the shift object.
        shift.setDoctorId(doctorId);
        shift.setShiftDate(shiftDate);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setRoom(room);
        shift.setShiftType(shiftType);

     // Call the service method to assign the shift and get the result message.
        String result = shiftService.assignShift(shift);
        
     // Set the result message as a request attribute and forward to the JSP page for display.
        request.setAttribute("message", result);
        request.getRequestDispatcher("/doctorShiftPage.jsp").forward(request, response);        
    }
}
