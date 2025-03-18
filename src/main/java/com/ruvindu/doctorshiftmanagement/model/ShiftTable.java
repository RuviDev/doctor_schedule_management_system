package com.ruvindu.doctorshiftmanagement.model;

public class ShiftTable {
	private int id;
    private String doctorName;
    private String shiftDate;
    private String shiftType;
    private String startTime;
    private String endTime;
    private String roomNumber;

	// Getters and Setters
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    public String getDoctorName() {
        return doctorName;
    }

	public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }
    
    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getFormattedTimeRange() {
        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");
        return startTimeParts[0] + ":" + startTimeParts[1] + " - " + endTimeParts[0] + ":" + endTimeParts[1];
    }
}
