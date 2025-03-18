package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ruvindu.doctorshiftmanagement.model.Room;
import com.ruvindu.doctorshiftmanagement.util.DatabaseConnection;

public class RoomDAOImpl implements RoomDAO {

	// Retrieves the list of rooms for the specified department.
    @Override
    public List<Room> getRoomsByDepartment(int departmentId) {
        List<Room> rooms = new ArrayList<>();
        
        try {
        	Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT id, room_number FROM room WHERE department_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Room room = new Room(rs.getInt("id"), rs.getString("room_number"));
                rooms.add(room);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rooms;
    }
}
