package com.ruvindu.doctorshiftmanagement.service;

import com.ruvindu.doctorshiftmanagement.dao.ShiftDAO;
import com.ruvindu.doctorshiftmanagement.model.Shift;
import com.ruvindu.doctorshiftmanagement.util.ShiftValidator;

public class ShiftService {
    private ShiftDAO shiftDAO;

    // Constructor to initialize the DAO
    public ShiftService(ShiftDAO shiftDAO) {
        this.shiftDAO = shiftDAO;
    }

    // Validate the data and if there is no any conflicts save the shift
    public String assignShift(Shift shift) {
        if (ShiftValidator.isPastTime(shift.getShiftDate(), shift.getStartTime())) {
            return "Cannot assign a shift in the past.";
        }
        if (!shiftDAO.checkDoctorAvailability(shift.getDoctorId(), shift.getStartTime(), shift.getEndTime())) {
            return "Doctor is not available during the selected time.";
        }
        if (shiftDAO.checkDoctorConflict(shift.getDoctorId(), shift.getShiftDate(), shift.getStartTime(), shift.getEndTime())) {
            return "Doctor already has a conflicting shift.";
        }
        if (shiftDAO.checkRoomConflict(shift.getRoom(), shift.getShiftDate(), shift.getStartTime(), shift.getEndTime())) {
            return "Room is already in use during this time.";
        }

        shiftDAO.saveShift(shift);
        return "Shift successfully assigned!";
    }
}
