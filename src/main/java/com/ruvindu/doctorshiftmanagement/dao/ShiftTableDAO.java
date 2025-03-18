package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ruvindu.doctorshiftmanagement.model.ShiftTable;
import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

public class ShiftTableDAO {

	// Retrieves the list of shift information for the specified department.
	public List<ShiftTable> getShiftsByDepartment(int depId) {
        List<ShiftTable> shiftList = new ArrayList<>();
        
        try {
        	Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT s.id, d.name, s.shift_date, s.shift_type, s.start_time, s.end_time, r.room_number " +
                    "FROM shift s, doctor d, department t, room r " +
                    "WHERE s.doctor_id = d.id " +
                    "AND d.department_id = t.id " +
                    "AND s.room_id = r.id " +
                    "AND t.id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, depId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShiftTable shiftT = new ShiftTable();
                shiftT.setId(rs.getInt("id"));
                shiftT.setDoctorName(rs.getString("name"));
                shiftT.setShiftDate(rs.getString("shift_date"));
                shiftT.setShiftType(rs.getString("shift_type"));
                shiftT.setStartTime(rs.getString("start_time")); 
                shiftT.setEndTime(rs.getString("end_time"));
                shiftT.setRoomNumber(rs.getString("room_number"));
                
                shiftList.add(shiftT);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shiftList;
    }
}
