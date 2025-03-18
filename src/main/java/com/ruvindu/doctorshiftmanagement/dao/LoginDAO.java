package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

public class LoginDAO {
	
	// Check whether the user exist in the database
	public boolean check(String uname, String pass) {
		try {
            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM operational_manager WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return false;
	}
}
