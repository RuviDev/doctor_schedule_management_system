package com.ruvindu.doctorshiftmanagement.controller;

import java.io.IOException;
import java.util.List;

import com.ruvindu.doctorshiftmanagement.dao.*;
import com.ruvindu.doctorshiftmanagement.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DoctorServlet")
public class DoctorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO classes are initiated directly without using a service class(No validations)
    private RoomDAO roomDAO = new RoomDAOImpl();
    private DoctorDAO doctorDAO = new DoctorDAOImpl();
    private ShiftTableDAO tableDAO = new ShiftTableDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departmentId = request.getParameter("department");
        int depId = Integer.parseInt(departmentId);       

        // Set the doctors as a request attribute to be forwarded to JSP
        List<Doctor> doctors = doctorDAO.getDoctorsByDepartment(depId);
        request.setAttribute("doctors", doctors);
        
        // Set the rooms as a request attribute to be forwarded to JSP
        List<Room> rooms = roomDAO.getRoomsByDepartment(depId);
        request.setAttribute("rooms", rooms);
        
        // Set the shifts as a request attribute to be forwarded to JSP
        List<ShiftTable> shifts = tableDAO.getShiftsByDepartment(depId);
        request.setAttribute("shifts", shifts);
        
        request.getRequestDispatcher("/doctorShiftPage.jsp").forward(request, response);
    }

}