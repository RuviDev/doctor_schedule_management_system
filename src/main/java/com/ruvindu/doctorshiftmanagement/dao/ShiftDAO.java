package com.ruvindu.doctorshiftmanagement.dao;

import com.ruvindu.doctorshiftmanagement.model.Shift;

public interface ShiftDAO {
	boolean checkDoctorAvailability(int doctorId, String startTime, String endTime);
    boolean checkDoctorConflict(int doctorId, String shiftDate, String startTime, String endTime);
    boolean checkRoomConflict(int room, String shiftDate, String startTime, String endTime);
    void saveShift(Shift shift);
}
