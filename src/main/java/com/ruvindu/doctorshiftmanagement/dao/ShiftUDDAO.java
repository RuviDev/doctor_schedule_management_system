package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ruvindu.doctorshiftmanagement.model.ShiftTable;

public class ShiftUDDAO {
	private Connection conn;
	private int docId;

    public ShiftUDDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Method to check if the doctor is available in the time range
    public boolean checkDoctorAvailability(String doctorName, String startTime, String endTime) {
        String query = "SELECT id, available_from, available_to FROM doctor WHERE name = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, doctorName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	docId = rs.getInt("id"); // Doctor id is taken for the use in checkDoctorConflict
                String availableFrom = rs.getString("available_from");
                String availableTo = rs.getString("available_to");

                // Check if shift's start and end times are within the doctor's available times
                if (startTime.compareTo(availableFrom) >= 0 && endTime.compareTo(availableTo) <= 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if there's any existing shift for a doctor within the time range
    public boolean checkDoctorConflict(int sid, String doctorName, String shiftDate, String startTime, String endTime) {
        String query = "SELECT COUNT(*) FROM shift WHERE doctor_id = ? AND shift_date = ? " +
                       "AND id != ? AND " +  // Exclude the shift with the given id
                       "((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, docId);
            stmt.setString(2, shiftDate);
            stmt.setInt(3, sid);
            stmt.setString(4, endTime);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, startTime);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Method to check if there's any existing shift between the time range in the same room
    public boolean checkRoomConflict(int sid, String roomN, String shiftDate, String startTime, String endTime) {
        int room = Integer.parseInt(roomN);
        String query = "SELECT COUNT(*) FROM shift WHERE room_id = ? AND shift_date = ? " +
                       "AND id != ? AND " +  // Exclude the shift with the given id
                       "((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, room);
            stmt.setString(2, shiftDate);
            stmt.setInt(3, sid);
            stmt.setString(4, endTime);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, startTime);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update a row in the shift table
    public void updateShiftDAO(ShiftTable shift) {
    	String updateShiftSQL = "UPDATE shift SET shift_date = ?, start_time = ?, end_time = ?, room_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(updateShiftSQL)) {
            stmt.setString(1, shift.getShiftDate());
            stmt.setString(2, shift.getStartTime());
            stmt.setString(3, shift.getEndTime());
            stmt.setInt(4, Integer.parseInt(shift.getRoomNumber()));
            stmt.setInt(5, shift.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to delete a row in the shift table
    public boolean deleteShift(int shiftId, String shiftType) {
    	String child_table;
    	if (shiftType.equals("Morning")) {
    		child_table = "morning_shift";
        } else if (shiftType.equals("Evening")) {
        	child_table = "evening_shift";
        } else {
        	child_table = "night_shift";
        }

        
        try {
        	// First, delete from the child table
            String deleteChildSQL = "DELETE FROM " + child_table + " WHERE id = ?";
            PreparedStatement childStmt = conn.prepareStatement(deleteChildSQL);
            childStmt.setInt(1, shiftId);
            childStmt.executeUpdate();

            // Then, delete from the main shift table
            String deleteShiftSQL = "DELETE FROM shift WHERE id = ?";
            PreparedStatement shiftStmt = conn.prepareStatement(deleteShiftSQL);
            shiftStmt.setInt(1, shiftId);
            
            int rowsDeleted = shiftStmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
