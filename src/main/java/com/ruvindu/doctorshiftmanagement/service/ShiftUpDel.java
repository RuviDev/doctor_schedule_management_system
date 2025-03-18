package com.ruvindu.doctorshiftmanagement.service;

import com.ruvindu.doctorshiftmanagement.dao.ShiftUDDAO;
import com.ruvindu.doctorshiftmanagement.model.ShiftTable;

public class ShiftUpDel {
	private ShiftUDDAO shiftUDDAO;

	// Constructor to initialize the DAO
    public ShiftUpDel(ShiftUDDAO shiftUDDAO) {
        this.shiftUDDAO = shiftUDDAO;
    }
	
    // Method to handle shift deletion based on shift ID and type
	public String deleteShiftById(int shiftId, String shiftType) {
        boolean isDeleted = shiftUDDAO.deleteShift(shiftId, shiftType);
        if (isDeleted) {
        	return "Shift successlly Deleted";
        }else {
        	return "Failed to delete shift.";
        }
    }
	
	// Method to update the shift, checks for doctor availability, conflicts, and room usage before updating the shift
	public String updateShift(ShiftTable shift) {
		if (!shiftUDDAO.checkDoctorAvailability(shift.getDoctorName(), shift.getStartTime(), shift.getEndTime())) {
            return "Doctor is not available during the selected time.";
        }
        if (shiftUDDAO.checkDoctorConflict(shift.getId(), shift.getDoctorName(), shift.getShiftDate(), shift.getStartTime(), shift.getEndTime())) {
            return "Doctor already has a conflicting shift.";
        }
        if (shiftUDDAO.checkRoomConflict(shift.getId(), shift.getRoomNumber(), shift.getShiftDate(), shift.getStartTime(), shift.getEndTime())) {
            return "Room is already in use during this time.";
        }
        
        shiftUDDAO.updateShiftDAO(shift);
        return "Shift successfully Updated!";
	}
	
}
