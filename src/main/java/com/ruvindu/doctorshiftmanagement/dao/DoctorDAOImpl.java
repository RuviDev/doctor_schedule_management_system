package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ruvindu.doctorshiftmanagement.model.Doctor;
import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

public class DoctorDAOImpl implements DoctorDAO{
	
	// Retrieves the list of doctor information for the specified department.
	@Override
	public List<Doctor> getDoctorsByDepartment(int depId) {
        List<Doctor> doctorList = new ArrayList<>();
        
        try {
        	Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT `id`, `name`, `email`, `available_from`, `available_to`, `phone`, `address`, `profile_img` " +
                         "FROM `doctor` WHERE `department_id` = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, depId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setAvailableTime(rs.getString("available_from") + " - " + rs.getString("available_to"));
                doctor.setProfilePic(rs.getString("profile_img"));
                doctorList.add(doctor);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorList;
    }
}
