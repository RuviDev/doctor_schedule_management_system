package com.ruvindu.doctorshiftmanagement.dao;

import java.sql.*;

import com.ruvindu.doctorshiftmanagement.model.*;

public class ShiftDAOImpl implements ShiftDAO {
    private Connection conn;

    // Constructor to initialize the DAO with a database connection
    public ShiftDAOImpl(Connection conn) {
        this.conn = conn;
    }
    
    // Check if the doctor is available during the shift times (start_time, end_time)
    @Override
    public boolean checkDoctorAvailability(int doctorId, String startTime, String endTime) {
        String query = "SELECT available_from, available_to FROM doctor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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

    // Check if there is a conflict in the doctor's schedule for a given date and time
    @Override
    public boolean checkDoctorConflict(int doctorId, String shiftDate, String startTime, String endTime) {
        String query = "SELECT COUNT(*) FROM shift WHERE doctor_id = ? AND shift_date = ? AND " +
                       "((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, shiftDate);
            stmt.setString(3, endTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, startTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if there is a conflict in the room schedule for a given date and time
    @Override
    public boolean checkRoomConflict(int room, String shiftDate, String startTime, String endTime) {
        String query = "SELECT COUNT(*) FROM shift WHERE room_id = ? AND shift_date = ? AND " +
                       "((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, room);
            stmt.setString(2, shiftDate);
            stmt.setString(3, endTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, startTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save the shift details in the database
    @Override
    public void saveShift(Shift shift) {
        String insertShift = "INSERT INTO shift (doctor_id, shift_date, start_time, end_time, room_id, shift_type) " +
                             "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertShift, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, shift.getDoctorId());
            stmt.setString(2, shift.getShiftDate());
            stmt.setString(3, shift.getStartTime());
            stmt.setString(4, shift.getEndTime());
            stmt.setInt(5, shift.getRoom());
            stmt.setString(6, shift.getShiftType());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys(); // Get the generated shift ID
            if (generatedKeys.next()) {
                int shiftId = generatedKeys.getInt(1);
                // call other functions according to the type of the passed object
                if (shift instanceof MorningShift) {
                    saveMorningShift(shiftId, (MorningShift) shift);
                } else if (shift instanceof EveningShift) {
                    saveEveningShift(shiftId, (EveningShift) shift);
                } else if (shift instanceof NightShift) {
                    saveNightShift(shiftId, (NightShift) shift);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Save details specific to morning shifts in the database
    private void saveMorningShift(int shiftId, MorningShift morningShift) throws SQLException {
        String query = "INSERT INTO morning_shift (id, special_morning_attr) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shiftId);
            stmt.setString(2, morningShift.getSpecialMorningAttr());
            stmt.executeUpdate();
        }
    }

 // Save details specific to evening shifts in the database
    private void saveEveningShift(int shiftId, EveningShift eveningShift) throws SQLException {
        String query = "INSERT INTO evening_shift (id, special_evening_attr) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shiftId);
            stmt.setString(2, eveningShift.getSpecialEveningAttr());
            stmt.executeUpdate();
        }
    }

 // Save details specific to night shifts in the database
    private void saveNightShift(int shiftId, NightShift nightShift) throws SQLException {
        String query = "INSERT INTO night_shift (id, special_night_attr) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shiftId);
            stmt.setString(2, nightShift.getSpecialNightAttr());
            stmt.executeUpdate();
        }
    }
}
